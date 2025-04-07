package com.nas.atm_machine.atm.service;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.dto.AccountBalance;
import com.nas.atm_machine.account.service.AccountBalanceBuilder;
import com.nas.atm_machine.account.service.AccountService;
import com.nas.atm_machine.atm.ATM;
import com.nas.atm_machine.atm.exception.ATMException;
import com.nas.atm_machine.atm.exception.ATMOutOfCashException;
import com.nas.atm_machine.atm.exception.InsufficientFundsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
public class ATMServiceImpl implements ATMService {

    private final AccountService accountService;

    private final ATM atm;


    public ATMServiceImpl(AccountService accountService) {
        this.accountService = accountService;
        this.atm = ATM.getInstance();

        BigDecimal amountAvailable = new BigDecimal(1500);
        Map<Integer, Integer> notesAvailableMap = new HashMap<>();
        // number of notes and the size of the note
        notesAvailableMap.put(50, 10);
        notesAvailableMap.put(20, 30);
        notesAvailableMap.put(10, 30);
        notesAvailableMap.put(5, 20);

        atm.setAmountAvailable(amountAvailable);
        atm.setNotesAvailableMap( notesAvailableMap);
    }

    @Override
    @Transactional // Ensure atomicity
    public AccountBalance withdrawal(Long accountNumber, String amountRequested) {
        BigDecimal amount = new BigDecimal(amountRequested);
        Account account = accountService.getAccountByAccountNumber(accountNumber);

        if (account.getAvailableBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account.");
        }

        if (atm.getAmountAvailable().compareTo(amount) < 0) {
            throw new ATMOutOfCashException("ATM does not have sufficient cash.");
        }

        Map<Integer, Integer> dispensedNotes = dispenseNotes(amount);
        if (dispensedNotes == null) {
            throw new ATMException("Could not dispense the exact amount with available notes.");
        }

        // Update account balance
        BigDecimal newOpeningBalance = account.getOpeningBalance().subtract(amount);
        BigDecimal newOverDraft = account.getOverDraft();
        if (newOpeningBalance.compareTo(BigDecimal.ZERO) < 0) {
            newOverDraft = newOverDraft.add(newOpeningBalance);
            newOpeningBalance = BigDecimal.ZERO;
        }
        account.setOpeningBalance(newOpeningBalance);
        account.setOverDraft(newOverDraft);

        // Update ATM cash
        updateATMNotes(dispensedNotes);
        atm.setAmountAvailable(atm.getAmountAvailable().subtract(amount));

        return new AccountBalanceBuilder()
                .setBalance(account.getOpeningBalance())
                .setAvailableBalance(account.getAvailableBalance())
                .setAmountWithdrew(amount)
                .setDispensedNotes(dispensedNotes)
                .build();
    }

    private Map<Integer, Integer> dispenseNotes(BigDecimal amount) {
        Map<Integer, Integer> dispensedNotes = new HashMap<>();
        Map<Integer, Integer> availableNotes = new TreeMap<>(atm.getNotesAvailableMap()).descendingMap(); // Process largest first
        BigDecimal remainingAmount = amount;

        for (Map.Entry<Integer, Integer> entry : availableNotes.entrySet()) {
            int noteValue = entry.getKey();
            int availableCount = entry.getValue();
            int notesToDispense = remainingAmount.divideToIntegralValue(BigDecimal.valueOf(noteValue)).intValue();
            notesToDispense = Math.min(notesToDispense, availableCount);

            if (notesToDispense > 0) {
                dispensedNotes.put(noteValue, notesToDispense);
                remainingAmount = remainingAmount.subtract(BigDecimal.valueOf(noteValue).multiply(BigDecimal.valueOf(notesToDispense)));
            }
        }

        if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            return dispensedNotes;
        } else {
            return null; // Could not dispense exact amount
        }
    }

    private void updateATMNotes(Map<Integer, Integer> dispensedNotes) {
        Map<Integer, Integer> currentNotes = atm.getNotesAvailableMap();
        for (Map.Entry<Integer, Integer> entry : dispensedNotes.entrySet()) {
            int noteValue = entry.getKey();
            int dispensedCount = entry.getValue();
            currentNotes.put(noteValue, currentNotes.get(noteValue) - dispensedCount);
        }
    }

    @Override
    public AccountBalance checkBalance(Long accountNumber) {
        return accountService.checkBalance(accountNumber);
    }
}

package com.nas.atm_machine.atm;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.repository.AccountRepository;
import com.nas.atm_machine.account.service.AccountBalance;
import com.nas.atm_machine.account.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ATMServiceImpl implements ATMService{

    private final AccountService accountService;

    private final ATM atm;


    public ATMServiceImpl(AccountService accountService) {
        this.accountService = accountService;
        this.atm = ATM.getInstance();

        BigDecimal amountAvailable = new BigDecimal(1500);
        Map<Integer, Integer> notesAvailableMap = new HashMap<>();
        // number of notes and the size of the note
        notesAvailableMap.put(10, 50);
        notesAvailableMap.put(30, 20);
        notesAvailableMap.put(30, 10);
        notesAvailableMap.put(20, 5);

        atm.setAmountAvailable(amountAvailable);
        atm.setNotesAvailableMap( notesAvailableMap);
    }


    @Override
    @Transactional
    public AccountBalance withdrawal(Long accountNumber, String amountRequested) {
        log.info("withdrawal amount: {}", amountRequested);

           BigDecimal amount = new BigDecimal(amountRequested);
           Account account = accountService.getAccountByAccountNumber(accountNumber);

           // check if the account balance in less or equal to the withdrawal amount
           if (account.getAvailableBalance().compareTo(amount)  < 0) {
               throw new RuntimeException("No sufficient fund!");

               // check if the maximum amount available in the ATM is higher or equal to the withdrawal amount
           } else if (atm.getAmountAvailable().compareTo(amount)  < 0) {
               throw new RuntimeException("Maximum amount available in the ATM is: " + atm.getAmountAvailable());
           }

           //update the account opening balance
           BigDecimal currentOpeningBalance = account.getOpeningBalance().subtract(amount);

           if (currentOpeningBalance.compareTo(new BigDecimal("0.00")) < 0) {
               BigDecimal currentOverDraft = account.getOverDraft().add(currentOpeningBalance);

               account.setOpeningBalance(new BigDecimal("0.00"));
               account.setOverDraft(currentOverDraft);
           }else {
               account.setOpeningBalance(currentOpeningBalance);
           }

           // get the amount in notes
       /* BigDecimal amountCopy = new BigDecimal(amount.toString());
        for (Integer entry : notesAvailableMap.keySet()) {

        }*/

           AccountBalance accountBalance = new AccountBalance();
           accountBalance.setBalance(account.getOpeningBalance());
           accountBalance.setAvailableBalance(account.getAvailableBalance());

           // update the atm maximum ammout
           atm.setAmountAvailable(atm.getAmountAvailable().subtract(amount));

           return accountBalance;
    }

    @Override
    public AccountBalance checkBalance(Long accountNumber) {
        return accountService.checkBalance(accountNumber);
    }
}

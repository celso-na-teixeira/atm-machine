package com.nas.atm_machine.account.service;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.dto.AccountBalance;
import com.nas.atm_machine.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountBalance checkBalance(Long accountNumber) {
        log.info("Checking balance");
        Account account = getAccountByAccountNumber(accountNumber);
        return new AccountBalanceBuilder()
                .setBalance(account.getOpeningBalance())
                .setAvailableBalance(account.getAvailableBalance())
                .build();
    }

    @Override
    public Account getAccountByAccountNumber(Long accountNumber) {
        log.info("Getting account by accountNumber");
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

}

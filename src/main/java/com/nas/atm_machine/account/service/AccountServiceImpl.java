package com.nas.atm_machine.account.service;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

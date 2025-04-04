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

    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(PasswordEncoder passwordEncoder, AccountRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public AccountBalance checkBalance(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return new AccountBalance(account.getOpeningBalance(), account.getAvailableBalance());
    }

    @Override
    public Account getAccountByAccountNumber(Long accountNumber) {
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
    }

    @Override
    public void saveAccount(Account account) {
        repository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        List <Account> accounts = repository.findAll();
        if (accounts == null | accounts.isEmpty()) {
            log.error("Accounts not found!");
            throw new RuntimeException();
        }
        return accounts;
    }


}

package com.nas.atm_machine.auth;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.repository.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AccountAuthServiceImpl implements AccountAuthService {


    private final AccountRepository repository;

    public AccountAuthServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        Long number = Long.parseLong(accountNumber);
        Account account = repository.findByAccountNumber(number).orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        return new org.springframework.security.core.userdetails.User(
                Long.toString(account.getAccountNumber()),
                account.getPin(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) // Default role
        );
    }

}

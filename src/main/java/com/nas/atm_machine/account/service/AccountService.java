package com.nas.atm_machine.account.service;

import com.nas.atm_machine.account.dao.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public AccountBalance checkBalance(Long accountNumber);

    Account getAccountByAccountNumber(Long accountNumber);

}

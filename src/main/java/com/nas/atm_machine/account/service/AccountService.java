package com.nas.atm_machine.account.service;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.dto.AccountBalance;

public interface AccountService {

    public AccountBalance checkBalance(Long accountNumber);

    Account getAccountByAccountNumber(Long accountNumber);

}

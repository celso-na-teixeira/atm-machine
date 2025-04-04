package com.nas.atm_machine.atm.service;

import com.nas.atm_machine.account.service.AccountBalance;

public interface ATMService {

    public AccountBalance withdrawal(Long accountNumber, String amount);

    public AccountBalance checkBalance(Long accountNumber);

}

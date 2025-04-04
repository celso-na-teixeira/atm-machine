package com.nas.atm_machine.atm;

import com.nas.atm_machine.account.service.AccountBalance;

import java.math.BigDecimal;

public interface ATMService {

    public AccountBalance withdrawal(Long accountNumber, String amount);

    public AccountBalance checkBalance(Long accountNumber);

}

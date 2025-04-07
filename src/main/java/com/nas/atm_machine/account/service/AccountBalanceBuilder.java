package com.nas.atm_machine.account.service;

import com.nas.atm_machine.account.dto.AccountBalance;

import java.math.BigDecimal;
import java.util.Map;

public class AccountBalanceBuilder {
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private BigDecimal amountWithdrew;
    Map<Integer, Integer> dispensedNotes;


    public AccountBalanceBuilder setBalance(BigDecimal balance) {
        this.balance=balance;
        return this;
    }

    public AccountBalanceBuilder setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public AccountBalanceBuilder setAmountWithdrew(BigDecimal amountWithdrew) {
        this.amountWithdrew = amountWithdrew;
        return this;
    }

    public AccountBalanceBuilder setDispensedNotes(Map<Integer, Integer> dispensedNotes) {
        this.dispensedNotes = dispensedNotes;
        return this;
    }

    public AccountBalance build() {
        return new AccountBalance(this.balance, this.availableBalance, this.amountWithdrew, this.dispensedNotes);
    }
}

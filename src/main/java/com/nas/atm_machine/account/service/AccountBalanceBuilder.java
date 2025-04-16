package com.nas.atm_machine.account.service;

import com.nas.atm_machine.account.dao.MonetaryAmount;
import com.nas.atm_machine.account.dto.AccountBalance;

import java.math.BigDecimal;
import java.util.Map;

public class AccountBalanceBuilder {
    private MonetaryAmount balance;
    private MonetaryAmount availableBalance;
    private MonetaryAmount amountWithdrew;
    Map<Integer, Integer> dispensedNotes;


    public AccountBalanceBuilder setBalance(MonetaryAmount balance) {
        this.balance=balance;
        return this;
    }

    public AccountBalanceBuilder setAvailableBalance(MonetaryAmount availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public AccountBalanceBuilder setAmountWithdrew(MonetaryAmount amountWithdrew) {
        this.amountWithdrew = amountWithdrew;
        return this;
    }

    public AccountBalanceBuilder setDispensedNotes(Map<Integer, Integer> dispensedNotes) {
        this.dispensedNotes = dispensedNotes;
        return this;
    }

    public AccountBalance build() {
        return new AccountBalance(this.balance.asBigDecimal(), this.availableBalance.asBigDecimal(), this.amountWithdrew.asBigDecimal(), this.dispensedNotes);
    }
}

package com.nas.atm_machine.account.service;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountBalance {

    private BigDecimal balance;
    private BigDecimal availableBalance;

    public AccountBalance(BigDecimal balance) {
        this.balance = balance;
    }

}

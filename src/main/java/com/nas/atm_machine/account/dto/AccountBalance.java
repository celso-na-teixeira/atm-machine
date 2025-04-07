package com.nas.atm_machine.account.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AccountBalance implements Serializable {

    private BigDecimal balance;
    private BigDecimal availableBalance;
    private BigDecimal amountWithdrew;
    Map<Integer, Integer> dispensedNotes;


}

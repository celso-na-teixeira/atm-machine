package com.nas.atm_machine.atm;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
public class ATM {
    private BigDecimal AmountAvailable;
    private Map<Integer, Integer> notesAvailableMap;

    private static ATM atm;

    private ATM() {

    }

    public static ATM getInstance() {
        if (atm == null) {
            atm = new ATM();
        }

        return atm;
    }
}

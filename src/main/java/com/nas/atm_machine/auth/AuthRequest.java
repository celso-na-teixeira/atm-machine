package com.nas.atm_machine.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    private String accountNumber;
    private String pin;

    @Override
    public String toString() {
        return "AuthRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}

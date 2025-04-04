package com.nas.atm_machine.atm.exception;

public class ATMOutOfCashException extends RuntimeException {
    public ATMOutOfCashException() {
        super();
    }

    public ATMOutOfCashException(String message) {
        super(message);
    }

    public ATMOutOfCashException(String message, Throwable cause) {
        super(message, cause);
    }

    public ATMOutOfCashException(Throwable cause) {
        super(cause);
    }
}

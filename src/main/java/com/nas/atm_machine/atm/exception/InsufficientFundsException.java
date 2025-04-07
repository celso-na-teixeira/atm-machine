package com.nas.atm_machine.atm.exception;

public class InsufficientFundsException extends BaseException {

    public static String shortMessage = "Insufficient funds";

    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }
}

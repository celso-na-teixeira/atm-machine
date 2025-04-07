package com.nas.atm_machine.atm.exception;

public class ATMException extends BaseException {

    public static String shortMessage = "Withdrawal failed";

    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }

    public ATMException(String message, Throwable cause) {
        super(message, cause);
    }

    public ATMException(Throwable cause) {
        super(cause);
    }
}

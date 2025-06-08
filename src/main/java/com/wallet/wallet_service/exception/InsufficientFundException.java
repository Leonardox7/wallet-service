package com.wallet.wallet_service.exception;

public class InsufficientFundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InsufficientFundException() {
        super("Insufficient funds");
    }

}
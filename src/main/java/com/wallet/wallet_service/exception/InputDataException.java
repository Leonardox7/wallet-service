package com.wallet.wallet_service.exception;

public class InputDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InputDataException(String message) {
        super(message);
    }

    public InputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputDataException(Throwable cause) {
        super(cause);
    }
    
}

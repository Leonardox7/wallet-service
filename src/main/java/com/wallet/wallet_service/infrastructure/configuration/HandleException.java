package com.wallet.wallet_service.infrastructure.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wallet.wallet_service.exception.ConflictDataException;
import com.wallet.wallet_service.exception.DataNotFoundException;
import com.wallet.wallet_service.exception.InputDataException;
import com.wallet.wallet_service.exception.InsufficientFundException;

@RestControllerAdvice
public class HandleException {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InsufficientFundException.class)
    public ErrorResponse handleUserAlreadyExistsException(InsufficientFundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InputDataException.class)
    public ErrorResponse handleInputDataException(InputDataException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorResponse handleDataNotFoundException(DataNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictDataException.class)
    public ErrorResponse handleConflictDataException(ConflictDataException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    public record ErrorResponse(String message) {

    }
}

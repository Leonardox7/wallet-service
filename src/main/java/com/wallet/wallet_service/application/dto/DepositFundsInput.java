package com.wallet.wallet_service.application.dto;

import java.math.BigDecimal;

import com.wallet.wallet_service.exception.InputDataException;

public record DepositFundsInput(String userId, BigDecimal amount) {
    public DepositFundsInput {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InputDataException("Amount must be greater than zero");
        }
        if (userId == null || userId.isBlank()) {
            throw new InputDataException("User ID cannot be null or blank");
        }
    }

}

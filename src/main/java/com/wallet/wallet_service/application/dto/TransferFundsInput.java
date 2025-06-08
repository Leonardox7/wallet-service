package com.wallet.wallet_service.application.dto;

import java.math.BigDecimal;

import com.wallet.wallet_service.exception.InputDataException;

public record TransferFundsInput(String fromUserId, String toUserId, BigDecimal amount) {
    public TransferFundsInput {
        if (fromUserId == null || fromUserId.isBlank()) {
            throw new InputDataException("From User ID cannot be null or blank");
        }
        if (toUserId == null || toUserId.isBlank()) {
            throw new InputDataException("To User ID cannot be null or blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InputDataException("Amount must be greater than zero");
        }
        if (fromUserId.equals(toUserId)) {
            throw new InputDataException("From User ID and To User ID cannot be the same");
        }
    }
}

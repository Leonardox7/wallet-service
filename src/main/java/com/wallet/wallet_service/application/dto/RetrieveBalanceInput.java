package com.wallet.wallet_service.application.dto;

import com.wallet.wallet_service.exception.InputDataException;

public record RetrieveBalanceInput(String userId) {
    public RetrieveBalanceInput {
        if (userId == null || userId.isBlank()) {
            throw new InputDataException("User ID cannot be null or blank");
        }
    }
}

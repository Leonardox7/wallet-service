package com.wallet.wallet_service.application.dto;

import java.math.BigDecimal;

public record WithDrawFundsInput(String userId, BigDecimal amount) {
    public WithDrawFundsInput {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

}

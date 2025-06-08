package com.wallet.wallet_service.application.dto;

import java.time.LocalDateTime;

import com.wallet.wallet_service.exception.InputDataException;

public record RetrieveHistoricalBalanceInput(String userId, LocalDateTime startDate, LocalDateTime endDate) {
    public RetrieveHistoricalBalanceInput {
        if (userId == null || userId.isBlank()) {
            throw new InputDataException("Wallet ID cannot be null or blank");
        }
        if (startDate == null || endDate == null) {
            throw new InputDataException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new InputDataException("Start date cannot be after end date");
        }
    }
}

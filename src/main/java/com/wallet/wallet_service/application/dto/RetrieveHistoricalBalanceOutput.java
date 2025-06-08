package com.wallet.wallet_service.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RetrieveHistoricalBalanceOutput(BigDecimal amount, String operation, LocalDateTime date) {
}

package com.wallet.wallet_service.application.dto;

import java.math.BigDecimal;

public record RetrieveBalanceOutput(String walletId, BigDecimal balance) {

}

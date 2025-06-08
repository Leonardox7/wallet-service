package com.wallet.wallet_service.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HistoricalBalanceTest {
    @Test
    void shouldCreateHistoricalBalanceWithCorrectValues() {
        String walletId = "wallet-1";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal balance = new BigDecimal("50.00");
        String operation = "Deposit";
        String description = "Initial deposit";
        HistoricalBalance hb = new HistoricalBalance(walletId, now, balance, operation, description);
        assertEquals(walletId, hb.getWalletId());
        assertEquals(now, hb.getDate());
        assertEquals(balance, hb.getAmount());
        assertEquals(operation, hb.getOperation());
        assertEquals(description, hb.getDescription());
    }

    @Test
    void toStringShouldContainKeyFields() {
        HistoricalBalance hb = new HistoricalBalance("id", LocalDateTime.now(), BigDecimal.TEN, "Deposit", "desc");
        String str = hb.toString();
        assertTrue(str.contains("walletId"));
        assertTrue(str.contains("operation"));
    }

    @Test
    void equalsShouldWorkForSameValues() {
        LocalDateTime now = LocalDateTime.now();
        HistoricalBalance hb1 = new HistoricalBalance("id", now, BigDecimal.TEN, "Deposit", "desc");
        HistoricalBalance hb2 = new HistoricalBalance("id", now, BigDecimal.TEN, "Deposit", "desc");
        assertEquals(hb1, hb2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentValues() {
        LocalDateTime now = LocalDateTime.now();
        HistoricalBalance hb1 = new HistoricalBalance("id1", now, BigDecimal.TEN, "Deposit", "desc");
        HistoricalBalance hb2 = new HistoricalBalance("id2", now, BigDecimal.ONE, "Withdraw", "other");
        assertNotEquals(hb1, hb2);
    }
}

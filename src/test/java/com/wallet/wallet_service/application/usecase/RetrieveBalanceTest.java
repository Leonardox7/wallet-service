package com.wallet.wallet_service.application.usecase;

import com.wallet.wallet_service.application.dto.RetrieveBalanceInput;
import com.wallet.wallet_service.application.dto.RetrieveBalanceOutput;
import com.wallet.wallet_service.domain.model.Wallet;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RetrieveBalanceTest {
    private WalletsRepository walletsRepository;
    private RetrieveBalance retrieveBalance;

    @BeforeEach
    void setUp() {
        walletsRepository = mock(WalletsRepository.class);
        retrieveBalance = new RetrieveBalance(walletsRepository);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenWalletNotFound() {
        RetrieveBalanceInput input = mock(RetrieveBalanceInput.class);

        UUID uuid = UUID.randomUUID();

        when(input.userId()).thenReturn(uuid.toString());
        when(walletsRepository.findByUserId(uuid.toString())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> retrieveBalance.execute(input));
    }

    @Test
    void shouldReturnBalanceWhenWalletExists() {
        RetrieveBalanceInput input = mock(RetrieveBalanceInput.class);
        Wallet wallet = mock(Wallet.class);

        UUID uuid = UUID.randomUUID();
        BigDecimal balance = new BigDecimal("150.00");

        when(input.userId()).thenReturn(uuid.toString());
        when(walletsRepository.findByUserId(uuid.toString())).thenReturn(Optional.of(wallet));
        when(wallet.getId()).thenReturn(uuid);
        when(wallet.getBalance()).thenReturn(balance);

        RetrieveBalanceOutput output = retrieveBalance.execute(input);
        assertEquals(uuid.toString(), output.walletId());
        assertEquals(balance, output.balance());
    }
}

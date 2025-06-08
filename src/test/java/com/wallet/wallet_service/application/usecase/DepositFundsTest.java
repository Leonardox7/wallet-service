package com.wallet.wallet_service.application.usecase;

import com.wallet.wallet_service.application.dto.DepositFundsInput;
import com.wallet.wallet_service.domain.model.Wallet;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DepositFundsTest {
    private WalletsRepository walletsRepository;
    private DepositFunds depositFunds;

    @BeforeEach
    void setUp() {
        walletsRepository = mock(WalletsRepository.class);
        depositFunds = new DepositFunds(walletsRepository);
    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenWalletNotFound() {
        DepositFundsInput input = mock(DepositFundsInput.class);
        var uuid = UUID.randomUUID();
        when(input.userId()).thenReturn(uuid.toString());
        when(walletsRepository.findByUserId(uuid.toString())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> depositFunds.execute(input));
    }

    @Test
    void shouldNotDepositWhenAmountIsZero() {
        DepositFundsInput input = mock(DepositFundsInput.class);
        Wallet wallet = mock(Wallet.class);
        var uuid = UUID.randomUUID();
        when(input.userId()).thenReturn(uuid.toString());
        when(input.amount()).thenReturn(BigDecimal.ZERO);
        when(walletsRepository.findByUserId(uuid.toString())).thenReturn(Optional.of(wallet));
        depositFunds.execute(input);
        verify(wallet, times(1)).deposit(BigDecimal.ZERO);
        verify(walletsRepository, times(1)).save(wallet);
    }

    @Test
    void shouldNotDepositWhenAmountIsNegative() {
        DepositFundsInput input = mock(DepositFundsInput.class);
        Wallet wallet = mock(Wallet.class);
        var uuid = UUID.randomUUID();
        when(input.userId()).thenReturn(uuid.toString());
        when(input.amount()).thenReturn(new BigDecimal("-10.00"));
        when(walletsRepository.findByUserId(uuid.toString())).thenReturn(Optional.of(wallet));
        depositFunds.execute(input);
        verify(wallet, times(1)).deposit(new BigDecimal("-10.00"));
        verify(walletsRepository, times(1)).save(wallet);
    }

    @Test
    void shouldDepositSuccessfully() {
        DepositFundsInput input = mock(DepositFundsInput.class);
        Wallet wallet = mock(Wallet.class);
        var uuid = UUID.randomUUID();
        when(input.userId()).thenReturn(uuid.toString());
        when(input.amount()).thenReturn(new BigDecimal("100.00"));
        when(walletsRepository.findByUserId(uuid.toString())).thenReturn(Optional.of(wallet));
        depositFunds.execute(input);
        verify(wallet, times(1)).deposit(new BigDecimal("100.00"));
        verify(walletsRepository, times(1)).save(wallet);
    }
}

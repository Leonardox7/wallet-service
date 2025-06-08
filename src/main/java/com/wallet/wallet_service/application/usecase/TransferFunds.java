package com.wallet.wallet_service.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wallet.wallet_service.application.dto.TransferFundsInput;
import com.wallet.wallet_service.domain.model.Wallet;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;

@Service
public class TransferFunds {

    private final WalletsRepository walletsRepository;

    public TransferFunds(WalletsRepository walletsRepository) {
        this.walletsRepository = walletsRepository;
    }

    public void execute(TransferFundsInput input) {
        Wallet walletFrom = walletsRepository.findByUserId(input.fromUserId())
                .orElseThrow(() -> new DataNotFoundException("Wallet not found for user ID: " + input.fromUserId()));

        Wallet walletTo = walletsRepository.findByUserId(input.toUserId())
                .orElseThrow(() -> new DataNotFoundException("Wallet not found for user ID: " + input.toUserId()));

        walletFrom.clearHistoricalBalance();
        walletTo.clearHistoricalBalance();
        walletFrom.transfer(walletTo, input.amount());
        walletsRepository.saveAll(List.of(walletFrom, walletTo));
    }
}

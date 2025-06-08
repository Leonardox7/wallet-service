package com.wallet.wallet_service.application.usecase;

import org.springframework.stereotype.Service;

import com.wallet.wallet_service.application.dto.DepositFundsInput;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;

@Service
public class DepositFunds {

    private WalletsRepository walletsRepository;

    public DepositFunds(WalletsRepository walletsRepository) {
        this.walletsRepository = walletsRepository;
    }

    public void execute(DepositFundsInput input) {
        walletsRepository.findByUserId(input.userId())
                .ifPresentOrElse(
                        wallet -> {
                            wallet.deposit(input.amount());
                            walletsRepository.save(wallet);
                        },
                        () -> {
                            throw new DataNotFoundException("Wallet not found for user ID: " + input.userId());
                        });
    }
}
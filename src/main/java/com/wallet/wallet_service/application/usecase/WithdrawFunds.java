package com.wallet.wallet_service.application.usecase;

import org.springframework.stereotype.Service;

import com.wallet.wallet_service.application.dto.WithDrawFundsInput;
import com.wallet.wallet_service.domain.model.OperationType;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;
import com.wallet.wallet_service.exception.InsufficientFundException;

@Service
public class WithdrawFunds {

    private final WalletsRepository walletsRepository;

    public WithdrawFunds(WalletsRepository walletsRepository) {
        this.walletsRepository = walletsRepository;
    }

    public void execute(WithDrawFundsInput input) {
        walletsRepository.findByUserId(input.userId())
                .ifPresentOrElse(
                        wallet -> {
                            if (wallet.getBalance().compareTo(input.amount()) < 0) {
                                throw new InsufficientFundException();
                            }
                            wallet.clearHistoricalBalance();
                            wallet.withdraw(input.amount(), OperationType.WITHDRAW, "");
                            walletsRepository.save(wallet);
                        },
                        () -> {
                            throw new DataNotFoundException("Wallet not found for user ID: " + input.userId());
                        });
    }
}

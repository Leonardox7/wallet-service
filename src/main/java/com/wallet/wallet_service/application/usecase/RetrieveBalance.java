package com.wallet.wallet_service.application.usecase;

import org.springframework.stereotype.Service;

import com.wallet.wallet_service.application.dto.RetrieveBalanceInput;
import com.wallet.wallet_service.application.dto.RetrieveBalanceOutput;
import com.wallet.wallet_service.domain.model.Wallet;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;

@Service
public class RetrieveBalance {

    private final WalletsRepository walletsRepository;

    public RetrieveBalance(WalletsRepository walletsRepository) {
        this.walletsRepository = walletsRepository;
    }

    public RetrieveBalanceOutput execute(RetrieveBalanceInput input) {
        Wallet wallet = walletsRepository.findByUserId(input.userId())
                .orElseThrow(() -> new DataNotFoundException("Wallet not found for user ID: " + input.userId()));
        return new RetrieveBalanceOutput(wallet.getId().toString(), wallet.getBalance());
    }
}

package com.wallet.wallet_service.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.wallet.wallet_service.domain.model.HistoricalBalance;
import com.wallet.wallet_service.domain.model.Wallet;

public interface WalletsRepository {
    Optional<Wallet> findByUserId(String userId);

    List<HistoricalBalance> findHistoricalBalanceByUserIdAndDate(String userId, LocalDateTime startDate,
            LocalDateTime endDate);

    void save(Wallet wallet);

    void saveAll(List<Wallet> wallet);
}

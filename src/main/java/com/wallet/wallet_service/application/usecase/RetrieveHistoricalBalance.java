package com.wallet.wallet_service.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wallet.wallet_service.application.dto.RetrieveHistoricalBalanceInput;
import com.wallet.wallet_service.application.dto.RetrieveHistoricalBalanceOutput;
import com.wallet.wallet_service.domain.model.HistoricalBalance;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.exception.DataNotFoundException;

@Service
public class RetrieveHistoricalBalance {

    private WalletsRepository walletsRepository;

    public RetrieveHistoricalBalance(WalletsRepository walletsRepository) {
        this.walletsRepository = walletsRepository;
    }

    public List<RetrieveHistoricalBalanceOutput> execute(RetrieveHistoricalBalanceInput input) {
        List<HistoricalBalance> historicalBalances = walletsRepository.findHistoricalBalanceByUserIdAndDate(
                input.userId(), input.startDate(), input.endDate());
        if (historicalBalances == null || historicalBalances.isEmpty()) {
            throw new DataNotFoundException("Historical Balance not found for user: " + input.userId());
        }

        return historicalBalances.stream()
                .map(balance -> new RetrieveHistoricalBalanceOutput(balance.getAmount(), balance.getOperation(),
                        balance.getDate()))
                .toList();
    }
}

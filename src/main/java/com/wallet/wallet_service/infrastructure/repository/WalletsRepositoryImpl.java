package com.wallet.wallet_service.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wallet.wallet_service.domain.model.HistoricalBalance;
import com.wallet.wallet_service.domain.model.Wallet;
import com.wallet.wallet_service.domain.repository.WalletsRepository;
import com.wallet.wallet_service.infrastructure.constant.DbConstant;
import com.wallet.wallet_service.infrastructure.entity.HistoricalBalanceEntity;
import com.wallet.wallet_service.infrastructure.entity.WalletEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class WalletsRepositoryImpl implements WalletsRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public WalletsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Wallet> findByUserId(String userId) {
        Optional<WalletEntity> walletEntity = entityManager
                .createQuery(DbConstant.FIND_WALLETS_BY_USER_ID, WalletEntity.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst();
        log.info("Wallet found for userId {}: founded: {}", userId, walletEntity.isPresent());
        return walletEntity.isPresent() ? Optional.of(walletEntity.get().toModel()) : Optional.empty();
    }

    @Override
    public List<HistoricalBalance> findHistoricalBalanceByUserIdAndDate(String userId, LocalDateTime startDate,
            LocalDateTime endDate) {
        List<HistoricalBalanceEntity> historicalBalances = entityManager
                .createQuery(DbConstant.FIND_HISTORICAL_BALANCE_BY_USER_ID_AND_DATE, HistoricalBalanceEntity.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return historicalBalances.stream()
                .map(HistoricalBalanceEntity::toModel).toList();
    }

    @Transactional
    @Override
    public void save(Wallet wallet) {
        WalletEntity walletEntity = new WalletEntity(wallet);
        entityManager.merge(walletEntity);
        log.info("Wallet saved for userId: {}", wallet.getUserId());
        entityManager.flush();
    }

    @Transactional
    @Override
    public void saveAll(List<Wallet> wallets) {
        List<WalletEntity> walletEntities = wallets.stream()
                .map(WalletEntity::new)
                .toList();
        for (WalletEntity entity : walletEntities) {
            save(entity.toModel());
        }

    }

}

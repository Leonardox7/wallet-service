package com.wallet.wallet_service.infrastructure.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.wallet.wallet_service.domain.model.Wallet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "Wallet")
@Table(name = "wallets", schema = "wallet")
public class WalletEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoricalBalanceEntity> historicalBalance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    public WalletEntity() {
    }

    public WalletEntity(Wallet wallet) {
        this.id = wallet.getId().toString();
        this.user = new UserEntity(wallet.getUserId().toString());
        this.balance = wallet.getBalance();
        this.historicalBalance = wallet.getHistoricalBalances().stream()
                .map(historicalBalance -> {
                    HistoricalBalanceEntity historical = new HistoricalBalanceEntity(historicalBalance);
                    historical.setWallet(this);
                    return historical;
                })
                .toList();
    }

    public Wallet toModel() {
        return new Wallet(UUID.fromString(id), UUID.fromString(user.getId()), balance, historicalBalance.stream()
                .map(HistoricalBalanceEntity::toModel).collect(Collectors.toSet()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return user != null ? user.getId() : null;
    }

    public void setUserId(String userId) {
        this.user.setId(userId);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<HistoricalBalanceEntity> getHistoricalBalance() {
        return historicalBalance;
    }

    public void setHistoricalBalance(List<HistoricalBalanceEntity> historicalBalance) {
        this.historicalBalance = historicalBalance;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

package com.wallet.wallet_service.infrastructure.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wallet.wallet_service.domain.model.HistoricalBalance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "HistoricalBalance")
@Table(name = "historical_balances", schema = "wallet")
public class HistoricalBalanceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime date;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "description")
    private String description;

    public HistoricalBalanceEntity() {
    }

    public HistoricalBalanceEntity(HistoricalBalance historicalBalance) {
        this.date = historicalBalance.getDate();
        this.amount = historicalBalance.getAmount();
        this.operation = historicalBalance.getOperation();
        this.description = historicalBalance.getDescription();
    }

    public HistoricalBalance toModel() {
        return new HistoricalBalance(
                wallet.getId(),
                date,
                amount,
                operation,
                description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

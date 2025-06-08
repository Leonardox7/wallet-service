package com.wallet.wallet_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.wallet.wallet_service.exception.InputDataException;
import com.wallet.wallet_service.exception.InsufficientFundException;

public class Wallet {
    private final UUID id;
    private final UUID userId;
    private BigDecimal balance;
    private Set<HistoricalBalance> historicalBalances;

    public Wallet(UUID userId) {
        this.id = UUID.randomUUID();
        this.balance = BigDecimal.ZERO;
        this.userId = userId;
        this.historicalBalances = new HashSet<>();
    }

    public Wallet(UUID id, UUID userId, BigDecimal balance, Set<HistoricalBalance> historicalBalances) {
        this.id = id;
        this.userId = userId;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.historicalBalances = historicalBalances != null ? historicalBalances : new HashSet<>();
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InputDataException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
        this.historicalBalances
                .add(new HistoricalBalance(id.toString(), LocalDateTime.now(), this.balance, "Deposit", ""));
    }

    public void withdraw(BigDecimal amount, OperationType operation, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InputDataException("Withdrawal amount must be positive");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new InsufficientFundException();
        }
        this.historicalBalances
                .add(new HistoricalBalance(id.toString(), LocalDateTime.now(), amount, operation.getValue(),
                        description));
        this.balance = this.balance.subtract(amount);
    }

    public void transfer(Wallet toWallet, BigDecimal amount) {
        if (toWallet == null) {
            throw new InputDataException("Target wallet cannot be null");
        }
        System.out.println("Transferring " + amount + " from wallet " + this.id + " to wallet " + toWallet.getId());

        this.withdraw(amount, OperationType.TRANSFER, "Transferred to wallet: " + toWallet.getId().toString());
        toWallet.deposit(amount);
    }

    public void clearHistoricalBalance() {
        this.historicalBalances.clear();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UUID getId() {
        return id;
    }

    public Set<HistoricalBalance> getHistoricalBalances() {
        return historicalBalances;
    }

    public UUID getUserId() {
        return userId;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Wallet wallet))
            return false;
        return id.equals(wallet.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", balance=" + balance +
                ", historicalBalances=" + historicalBalances +
                '}';
    }
}
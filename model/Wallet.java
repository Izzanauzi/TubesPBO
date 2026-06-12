package com.bidkita.bidkita_backend.model;

import com.bidkita.bidkita_backend.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "wallet_id")
    private String walletId = UUID.randomUUID().toString();

    @JsonIgnore
    @OneToOne(mappedBy = "wallet", fetch = FetchType.LAZY)
    private Buyer owner;

    @Column(nullable = false)
    private double balance = 0.0;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private List<WalletTransaction> transactions = new ArrayList<>();

    public Wallet() {}

    public String getWalletId() {
        return walletId;
    }

    public Buyer getOwner() {
        return owner;
    }

    public void setOwner(Buyer owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public List<WalletTransaction> getTransactions() {
        return transactions;
    }

    public List<WalletTransaction> getTransactionHistory() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah deposit harus lebih dari 0");
        }
        this.balance += amount;
        this.transactions.add(new WalletTransaction(amount, TransactionType.DEPOSIT));
    }

    public void depositRefund(double amount) {
        if (amount <= 0) {
            return;
        }
        this.balance += amount;
        this.transactions.add(new WalletTransaction(amount, TransactionType.REFUND));
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah withdraw harus lebih dari 0");
        }
        if (balance < amount) {
            return false;
        }
        this.balance -= amount;
        this.transactions.add(new WalletTransaction(amount, TransactionType.WITHDRAW));
        return true;
    }
}

package com.bidkita.bidkita_backend.dto.response;

import java.util.List;

public class WalletResponseDTO {

    private String walletId;
    private double balance;
    private List<WalletTransactionResponseDTO> transactions;

    public WalletResponseDTO() {}

    public WalletResponseDTO(String walletId, double balance, List<WalletTransactionResponseDTO> transactions) {
        this.walletId = walletId;
        this.balance = balance;
        this.transactions = transactions;
    }

    // TODO: implement after Model is complete
    // public WalletResponseDTO(Wallet wallet) { ... }

    public String getWalletId() { return walletId; }
    public void setWalletId(String walletId) { this.walletId = walletId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public List<WalletTransactionResponseDTO> getTransactions() { return transactions; }
    public void setTransactions(List<WalletTransactionResponseDTO> transactions) { this.transactions = transactions; }
}

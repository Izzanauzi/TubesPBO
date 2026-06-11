package com.bidkita.bidkita_backend.dto.response;

import java.util.Date;

public class WalletTransactionResponseDTO {

    private Long id;
    private double amount;
    private String type;
    private Date createdAt;

    public WalletTransactionResponseDTO() {}

    public WalletTransactionResponseDTO(Long id, double amount, String type, Date createdAt) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    // TODO: implement after Model is complete
    // public WalletTransactionResponseDTO(WalletTransaction transaction) { ... }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}

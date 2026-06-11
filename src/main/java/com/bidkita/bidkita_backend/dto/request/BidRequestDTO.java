package com.bidkita.bidkita_backend.dto.request;

public class BidRequestDTO {

    private double amount;

    public BidRequestDTO() {}

    public BidRequestDTO(double amount) {
        this.amount = amount;
    }

    public double getAmount() { 
        return amount; 
    }
    public void setAmount(double amount) {
        this.amount = amount; 
    }
}

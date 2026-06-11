package com.bidkita.bidkita_backend.dto.request;

public class TopUpRequestDTO {

    private double amount;
    private String paymentMethod;

    public TopUpRequestDTO() {}

    public TopUpRequestDTO(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

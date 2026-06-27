package com.bidkita.bidkita_backend.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Saldo wallet tidak mencukupi");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

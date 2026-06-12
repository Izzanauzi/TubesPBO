package com.bidkita.bidkita_backend.exception;

public class BidTooLowException extends RuntimeException {
    public BidTooLowException() {
        super("Bid harus lebih tinggi dari harga saat ini");
    }

    public BidTooLowException(String message) {
        super(message);
    }
}

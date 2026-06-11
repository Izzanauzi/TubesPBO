package com.bidkita.bidkita_backend.exception;

public class BidTooLowException extends RuntimeException {
    public BidTooLowException(String message) {
        super(message);
    }
}

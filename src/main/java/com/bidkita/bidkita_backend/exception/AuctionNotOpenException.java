package com.bidkita.bidkita_backend.exception;

public class AuctionNotOpenException extends RuntimeException {
    public AuctionNotOpenException(String message) {
        super(message);
    }
}

package com.bidkita.bidkita_backend.exception;

public class AuctionNotOpenException extends RuntimeException {
    public AuctionNotOpenException() {
        super("Lelang tidak dalam status OPEN atau CLOSING");
    }

    public AuctionNotOpenException(String message) {
        super(message);
    }
}

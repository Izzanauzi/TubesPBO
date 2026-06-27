package com.bidkita.bidkita_backend.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Email sudah terdaftar");
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

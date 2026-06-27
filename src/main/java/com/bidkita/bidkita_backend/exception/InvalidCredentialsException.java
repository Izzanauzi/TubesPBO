package com.bidkita.bidkita_backend.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Email atau password salah");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}

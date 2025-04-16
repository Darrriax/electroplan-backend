package com.example.electroplan_backend.exceptions.userbanner;

public class InvalidImageTypeException extends RuntimeException {
    public InvalidImageTypeException() {
    }

    public InvalidImageTypeException(String message) {
        super(message);
    }

    public InvalidImageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidImageTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidImageTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

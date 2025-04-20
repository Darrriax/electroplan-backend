package com.example.electroplan_backend.exceptions.password;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException(String message) {
        super(message);
    }
}
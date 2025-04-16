package com.example.electroplan_backend.exceptions.user;


/**
 * Коли авторизація невдала
 */
public class InvalidUserParametersException extends RuntimeException {
    public InvalidUserParametersException() {
    }

    public InvalidUserParametersException(String message) {
        super(message);
    }

    public InvalidUserParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserParametersException(Throwable cause) {
        super(cause);
    }

    public InvalidUserParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.example.electroplan_backend.exceptions.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Виникла помилка. Спробуйте пізніше.");
//    }

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName;
        if (error instanceof FieldError) {
            fieldName = ((FieldError) error).getField();
        } else {
            fieldName = error.getObjectName();
        }
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.badRequest().body(errors);
}

}

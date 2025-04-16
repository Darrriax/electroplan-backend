package com.example.electroplan_backend.exceptions.handlers;

import com.example.electroplan_backend.exceptions.user.InvalidCredentialsException;
import com.example.electroplan_backend.exceptions.user.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//http://localhost:8080/swagger-ui/index.html#/

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorResponse invalid(InvalidCredentialsException e){
        return ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse userAlreadyExists(UserAlreadyExistsException e){
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }


}

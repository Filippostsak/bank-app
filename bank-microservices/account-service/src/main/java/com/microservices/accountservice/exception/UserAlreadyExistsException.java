package com.microservices.accountservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

@Schema(description = "Exception for user already exists")
public class UserAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

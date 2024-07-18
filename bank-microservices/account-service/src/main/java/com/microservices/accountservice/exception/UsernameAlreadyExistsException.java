package com.microservices.accountservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

@Schema(description = "Exception for user already exists")
public class UsernameAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}

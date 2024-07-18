package com.microservices.accountservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * Exception thrown when an account already exists
 */
@Schema(description = "Exception for account already exists")
public class AccountAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistsException(String message){
        super(message);
    }
}

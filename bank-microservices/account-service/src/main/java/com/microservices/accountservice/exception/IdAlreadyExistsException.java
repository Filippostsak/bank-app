package com.microservices.accountservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

@Schema(description = "Exception for id already exists")
public class IdAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public IdAlreadyExistsException(String message){
        super(message);
    }
}

package com.microservices.accountservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

@Schema(description = "Exception for limit exceeded")
public class LimitExceededException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public LimitExceededException(String message){
        super(message);
    }
}

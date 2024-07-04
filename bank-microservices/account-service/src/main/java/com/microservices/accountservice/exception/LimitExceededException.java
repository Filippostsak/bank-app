package com.microservices.accountservice.exception;

import java.io.Serial;

public class LimitExceededException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public LimitExceededException(String message){
        super(message);
    }
}

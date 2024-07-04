package com.microservices.accountservice.exception;

import java.io.Serial;

public class IdAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public IdAlreadyExistsException(String message){
        super(message);
    }
}

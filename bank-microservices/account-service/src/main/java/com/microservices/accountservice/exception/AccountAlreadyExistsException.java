package com.microservices.accountservice.exception;

import java.io.Serial;

public class AccountAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistsException(String message){
        super(message);
    }
}

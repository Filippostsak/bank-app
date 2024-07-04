package com.microservices.accountservice.exception;

import java.io.Serial;

public class PasswordIsNotConfirmedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public PasswordIsNotConfirmedException(String message){
        super(message);
    }
}

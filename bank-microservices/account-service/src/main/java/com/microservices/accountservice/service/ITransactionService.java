package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.TransactionDTO;
import com.microservices.accountservice.exception.LimitExceededException;
import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.model.Limits;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ITransactionService {

    void processTransaction(TransactionDTO transactionDTO) throws LimitExceededException;

    void validateTransactionLimits(Account account, BigDecimal transactionAmount, Limits limits, LocalDateTime now) throws LimitExceededException;
}

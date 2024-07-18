package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.TransactionDTO;
import com.microservices.accountservice.exception.LimitExceededException;
import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.model.Limits;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service interface for managing transactions on accounts.
 */
public interface ITransactionService {

    /**
     * Adds money to the specified account.
     *
     * @param transactionDTO the transaction details.
     * @throws LimitExceededException if the transaction exceeds the limits.
     */
    void addMoney(TransactionDTO transactionDTO) throws LimitExceededException;

    /**
     * Withdraws money from the specified account.
     *
     * @param transactionDTO the transaction details.
     * @throws LimitExceededException if the transaction exceeds the limits.
     */
    void withdrawMoney(TransactionDTO transactionDTO) throws LimitExceededException;

    /**
     * Processes a transaction for the specified account.
     *
     * @param transactionDTO the transaction details.
     * @throws LimitExceededException if the transaction exceeds the limits.
     */
    void processTransaction(TransactionDTO transactionDTO) throws LimitExceededException;

    /**
     * Validates the transaction limits for a given account.
     *
     * @param account           the account to be validated.
     * @param transactionAmount the amount of the transaction.
     * @param limits            the limits of the account.
     * @param now               the current date and time.
     * @param isDeposit         true if the transaction is a deposit, false if it is a withdrawal.
     * @throws LimitExceededException if the transaction exceeds any limits.
     */
    void validateTransactionLimits(Account account, BigDecimal transactionAmount, Limits limits, LocalDateTime now, boolean isDeposit) throws LimitExceededException;
}

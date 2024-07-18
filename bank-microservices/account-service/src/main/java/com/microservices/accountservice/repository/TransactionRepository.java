package com.microservices.accountservice.repository;

import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for the Transaction entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions for a specific account that occurred between the given start and end dates.
     *
     * @param account   the account for which transactions are to be retrieved.
     * @param startDate the start date and time of the period to search for transactions.
     * @param endDate   the end date and time of the period to search for transactions.
     * @return a list of transactions that match the criteria.
     */
    List<Transaction> findByAccountAndTransactionDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);
}

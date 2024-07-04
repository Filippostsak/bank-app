package com.microservices.accountservice.repository;

import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByAccountAndTransactionDateBetween(Account account, LocalDateTime startDate, LocalDateTime endDate);
}

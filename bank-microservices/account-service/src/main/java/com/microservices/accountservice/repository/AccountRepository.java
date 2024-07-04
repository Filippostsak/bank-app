package com.microservices.accountservice.repository;

import com.microservices.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByUsername(String username);

    Boolean existsByAccountNumber(String s);

    Optional<Account> findByAccountNumber(String accountNumber);
}

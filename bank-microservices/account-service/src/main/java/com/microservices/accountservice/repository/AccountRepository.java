package com.microservices.accountservice.repository;

import com.microservices.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for the Account entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by the username.
     *
     * @param username the username to search for.
     * @return an Optional containing the found Account, or an empty Optional if no Account found.
     */
    Optional<Account> findByUsername(String username);

    /**
     * Checks if an account exists by the account number.
     *
     * @param accountNumber the account number to check for.
     * @return true if an account with the given account number exists, false otherwise.
     */
    Boolean existsByAccountNumber(String accountNumber);

    /**
     * Finds an account by the account number.
     *
     * @param accountNumber the account number to search for.
     * @return an Optional containing the found Account, or an empty Optional if no Account found.
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Finds an account by the account ID.
     *
     * @param id the ID of the account to search for.
     * @return an Optional containing the found Account, or an empty Optional if no Account found.
     */
    Optional<Account> findById(Long id);
}

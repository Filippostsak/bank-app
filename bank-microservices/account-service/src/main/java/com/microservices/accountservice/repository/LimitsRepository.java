package com.microservices.accountservice.repository;

import com.microservices.accountservice.model.Limits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for the Limits entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface LimitsRepository extends JpaRepository<Limits, Long> {

    /**
     * Finds the limits associated with a specific account ID.
     *
     * @param accountId the ID of the account to search for limits.
     * @return an Optional containing the found Limits, or an empty Optional if no Limits found for the account.
     */
    Optional<Limits> findByAccountId(Long accountId);
}

package com.microservices.userservice.repository;

import com.microservices.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User repository
 */
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);

    /**
     * Check if a user exists by username
     * @param username String
     * @return boolean
     */
    boolean existsByUsername(String username);

    /**
     * Find a user by username
     * @param username String
     * @return Optional<User>
     */

    Optional<User> findByUsername(String username);

    /**
     * Find a user by identity number
     * @param idNumber String
     * @return Optional<User>
     */
    boolean existsByIdNumber(String idNumber);
}

package com.microservices.userservice.repository;

import com.microservices.userservice.model.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Token Blacklist Repository
 */

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    /**
     * Find a token by token
     * @param token String
     * @return Optional<TokenBlacklist>
     */
    Optional<TokenBlacklist> findByToken(String token);
}

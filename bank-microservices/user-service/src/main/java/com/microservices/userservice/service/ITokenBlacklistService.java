package com.microservices.userservice.service;

/**
 * Token Blacklist Service Interface
 */

public interface ITokenBlacklistService {

    /**
     * Blacklist a token
     * @param token String
     */

    void blacklistToken(String token);

    /**
     * Check if a token is blacklisted
     * @param token String
     * @return boolean
     */

    boolean isTokenBlacklisted(String token);
}

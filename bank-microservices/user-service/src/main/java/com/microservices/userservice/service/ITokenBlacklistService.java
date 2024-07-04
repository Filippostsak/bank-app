package com.microservices.userservice.service;

public interface ITokenBlacklistService {

    void blacklistToken(String token);

    boolean isTokenBlacklisted(String token);
}

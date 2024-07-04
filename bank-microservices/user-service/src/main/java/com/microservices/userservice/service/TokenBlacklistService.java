package com.microservices.userservice.service;

import com.microservices.userservice.model.TokenBlacklist;
import com.microservices.userservice.repository.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService implements ITokenBlacklistService{

    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public void blacklistToken(String token) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setBlacklistedAt(LocalDateTime.now());
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.findByToken(token).isPresent();
    }
}

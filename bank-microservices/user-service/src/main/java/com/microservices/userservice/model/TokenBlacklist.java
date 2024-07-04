package com.microservices.userservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Token Blacklist Entity
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Token Blacklist entity")
public class TokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of the token blacklist", example = "1")
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    @Schema(description = "Token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiJ9")
    private String token;

    @Column(nullable = false)
    @Schema(description = "Blacklisted at", example = "2021-07-01T10:00:00")
    private LocalDateTime blacklistedAt;
}

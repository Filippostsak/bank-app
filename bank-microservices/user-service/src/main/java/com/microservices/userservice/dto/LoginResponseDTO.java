package com.microservices.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * LoginResponseDTO
 */

@Data
@AllArgsConstructor
@Schema(name = "LoginResponseDTO", description = "DTO for login response")
public class LoginResponseDTO {
    private String token;
}

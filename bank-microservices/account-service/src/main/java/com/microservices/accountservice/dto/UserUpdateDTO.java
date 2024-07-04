package com.microservices.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "UserUpdateDTO", description = "DTO for updating user information")
public class UserUpdateDTO {

    @NotNull(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Email(message = "Email must be a valid email address")
    @NotNull(message = "Email is required")
    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters")
    @Schema(description = "Email of the user", example = "filip@gmail.com")
    private String email;

}

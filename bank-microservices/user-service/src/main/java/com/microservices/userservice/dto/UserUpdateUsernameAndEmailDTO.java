package com.microservices.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateUsernameAndEmailDTO {

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

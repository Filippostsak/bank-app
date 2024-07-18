package com.microservices.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO for getting current logged in user")
public class UserGetCurrentLoggedInUserDTO {

    private Long id;

    private String username;

    private String email;
}

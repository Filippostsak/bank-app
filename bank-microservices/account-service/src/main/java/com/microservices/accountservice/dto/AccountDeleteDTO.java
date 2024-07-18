package com.microservices.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO for deleting account")
public class AccountDeleteDTO {

    private Long id;

    private String message="User deleted successfully";
}

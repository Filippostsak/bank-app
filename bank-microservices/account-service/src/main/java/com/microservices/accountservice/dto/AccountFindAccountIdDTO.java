package com.microservices.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "DTO for finding account by id")
public class AccountFindAccountIdDTO {

    private Long id;
    private String accountNumber;

}

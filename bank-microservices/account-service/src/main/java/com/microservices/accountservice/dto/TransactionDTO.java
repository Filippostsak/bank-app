package com.microservices.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Transaction data transfer object")
public class TransactionDTO {

    private String accountNumber;
    private BigDecimal amount;
}

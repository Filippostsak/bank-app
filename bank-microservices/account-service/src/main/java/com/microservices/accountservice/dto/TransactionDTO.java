package com.microservices.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDTO {

    private String accountNumber;
    private BigDecimal amount;
}

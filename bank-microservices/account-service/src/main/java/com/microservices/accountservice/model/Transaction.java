package com.microservices.accountservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Transaction", description = "Entity for storing transaction information")
public class Transaction extends AbstractEntity {

    @Column(name = "transaction_amount", nullable = false)
    @Schema(description = "Transaction amount", example = "100.00")
    private BigDecimal transactionAmount;

    @Column(name = "transaction_date", nullable = false)
    @Schema(description = "Transaction date and time", example = "2024-07-04T10:15:30")
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @Schema(description = "Associated account")
    private Account account;
}

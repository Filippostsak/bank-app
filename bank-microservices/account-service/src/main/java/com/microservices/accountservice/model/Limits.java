package com.microservices.accountservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Limits", description = "Entity for storing limits information")
public class Limits extends AbstractEntity {

    @Column(name = "daily_limit", nullable = false)
    @Schema(description = "Daily limit", example = "1500")
    private BigDecimal dailyLimit;

    @Column(name = "weekly_limit", nullable = false)
    @Schema(description = "Weekly limit", example = "7500")
    private BigDecimal weeklyLimit;

    @Column(name = "monthly_limit", nullable = false)
    @Schema(description = "Monthly limit", example = "30000")
    private BigDecimal monthlyLimit;

    @Column(name = "transaction_limit", nullable = false)
    @Schema(description = "Transaction limit", example = "600")
    private BigDecimal transactionLimit;

    @Column(name = "daily_transaction_limit", nullable = false)
    @Schema(description = "Daily transaction limit", example = "1500")
    private BigDecimal dailyTransactionLimit;

    @Column(name = "weekly_transaction_limit", nullable = false)
    @Schema(description = "Weekly transaction limit", example = "7500")
    private BigDecimal weeklyTransactionLimit;

    @Column(name = "monthly_transaction_limit", nullable = false)
    @Schema(description = "Monthly transaction limit", example = "30000")
    private BigDecimal monthlyTransactionLimit;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @Schema(description = "Account")
    private Account account;
}

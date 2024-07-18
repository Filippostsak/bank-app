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

    @Column(name = "daily_withdraw_limit", nullable = false)
    @Schema(description = "Daily withdrawal limit", example = "500")
    private BigDecimal dailyWithdrawLimit;

    @Column(name = "daily_deposit_limit", nullable = false)
    @Schema(description = "Daily deposit limit", example = "500")
    private BigDecimal dailyDepositLimit;

    @Column(name = "weekly_withdraw_limit", nullable = false)
    @Schema(description = "Weekly withdrawal limit", example = "3750")
    private BigDecimal weeklyWithdrawLimit;

    @Column(name = "weekly_deposit_limit", nullable = false)
    @Schema(description = "Weekly deposit limit", example = "3750")
    private BigDecimal weeklyDepositLimit;

    @Column(name = "monthly_withdraw_limit", nullable = false)
    @Schema(description = "Monthly withdrawal limit", example = "15000")
    private BigDecimal monthlyWithdrawLimit;

    @Column(name = "monthly_deposit_limit", nullable = false)
    @Schema(description = "Monthly deposit limit", example = "15000")
    private BigDecimal monthlyDepositLimit;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @Schema(description = "Account")
    private Account account;
}

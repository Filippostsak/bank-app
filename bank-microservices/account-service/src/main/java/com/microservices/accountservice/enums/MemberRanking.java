package com.microservices.accountservice.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Enumeration representing different member rankings and their respective transaction limits.
 */
@Getter
@Schema(description = "Enumeration representing different member rankings and their respective transaction limits.")
public enum MemberRanking {

    @Schema(description = "Standard ranking with basic limits.")
    STANDARD(new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("20000"), new BigDecimal("300")),

    @Schema(description = "Metal ranking with higher limits.")
    METAL(new BigDecimal("2000"), new BigDecimal("10000"), new BigDecimal("40000"), new BigDecimal("600")),

    @Schema(description = "Pro ranking with advanced limits.")
    PRO(new BigDecimal("5000"), new BigDecimal("25000"), new BigDecimal("100000"), new BigDecimal("1500")),

    @Schema(description = "Ultimate ranking with the highest limits.")
    ULTIMATE(new BigDecimal("10000"), new BigDecimal("50000"), new BigDecimal("200000"), new BigDecimal("3000"));

    @Schema(description = "Daily limit for transactions.")
    private final BigDecimal dailyLimit;

    @Schema(description = "Weekly limit for transactions.")
    private final BigDecimal weeklyLimit;

    @Schema(description = "Monthly limit for transactions.")
    private final BigDecimal monthlyLimit;

    @Schema(description = "Limit for a single transaction.")
    private final BigDecimal transactionLimit;

    @Schema(description = "Daily limit for withdrawals.")
    private final BigDecimal dailyWithdrawLimit;

    @Schema(description = "Daily limit for deposits.")
    private final BigDecimal dailyDepositLimit;

    @Schema(description = "Weekly limit for withdrawals.")
    private final BigDecimal weeklyWithdrawLimit;

    @Schema(description = "Weekly limit for deposits.")
    private final BigDecimal weeklyDepositLimit;

    @Schema(description = "Monthly limit for withdrawals.")
    private final BigDecimal monthlyWithdrawLimit;

    @Schema(description = "Monthly limit for deposits.")
    private final BigDecimal monthlyDepositLimit;

    /**
     * Constructor to initialize the limits for each member ranking.
     *
     * @param dailyLimit       The daily transaction limit.
     * @param weeklyLimit      The weekly transaction limit.
     * @param monthlyLimit     The monthly transaction limit.
     * @param transactionLimit The limit for a single transaction.
     */
    MemberRanking(BigDecimal dailyLimit, BigDecimal weeklyLimit, BigDecimal monthlyLimit, BigDecimal transactionLimit) {
        this.dailyLimit = dailyLimit;
        this.weeklyLimit = weeklyLimit;
        this.monthlyLimit = monthlyLimit;
        this.transactionLimit = transactionLimit;
        this.dailyWithdrawLimit = dailyLimit.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
        this.dailyDepositLimit = dailyLimit.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
        this.weeklyWithdrawLimit = weeklyLimit.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
        this.weeklyDepositLimit = weeklyLimit.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
        this.monthlyWithdrawLimit = monthlyLimit.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
        this.monthlyDepositLimit = monthlyLimit.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
    }
}

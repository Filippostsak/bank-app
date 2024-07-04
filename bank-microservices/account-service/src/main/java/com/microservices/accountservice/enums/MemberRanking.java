package com.microservices.accountservice.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum MemberRanking {

    STANDARD(new BigDecimal("1000"), new BigDecimal("5000"), new BigDecimal("20000"), new BigDecimal("300")),
    METAL(new BigDecimal("2000"), new BigDecimal("10000"), new BigDecimal("40000"), new BigDecimal("600")),
    PRO(new BigDecimal("5000"), new BigDecimal("25000"), new BigDecimal("100000"), new BigDecimal("1500")),
    ULTIMATE(new BigDecimal("10000"), new BigDecimal("50000"), new BigDecimal("200000"), new BigDecimal("3000"));

    private final BigDecimal dailyLimit;
    private final BigDecimal weeklyLimit;
    private final BigDecimal monthlyLimit;
    private final BigDecimal transactionLimit;

    MemberRanking(BigDecimal dailyLimit, BigDecimal weeklyLimit, BigDecimal monthlyLimit, BigDecimal transactionLimit) {
        this.dailyLimit = dailyLimit;
        this.weeklyLimit = weeklyLimit;
        this.monthlyLimit = monthlyLimit;
        this.transactionLimit = transactionLimit;
    }

}

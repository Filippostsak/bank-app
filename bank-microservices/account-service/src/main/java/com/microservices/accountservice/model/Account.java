package com.microservices.accountservice.model;

import com.microservices.accountservice.enums.MemberRanking;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Account", description = "Entity for storing account information")
public class Account extends AbstractEntity{

    @Column(unique = true, nullable = false)
    @Schema(description = "Username of the account", example = "admin")
    private String username;
    @Column(nullable = false)
    @Schema(description = "Email of the account", example = "filip@gmail.com")
    private String email;
    @Column(name = "account_number", unique = true, nullable = false)
    @Schema(description = "Account number", example = "1234567890")
    private String accountNumber;
    @Column(name = "member_ranking", nullable = false)
    @Schema(description = "Member ranking", example = "REGULAR")
    private MemberRanking memberRanking;
    @Column(nullable = false, precision = 19, scale = 4)
    @Schema(description = "Balance", example = "1000.00")
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Limits")
    private List<Limits> limits;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Transactions")
    private List<Transaction> transactions;
}

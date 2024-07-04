package com.microservices.accountservice.dto;

import com.microservices.accountservice.enums.MemberRanking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Account create data transfer object")
public class AccountCreateDTO {

    @Schema(description = "Username of the account", example = "user123")
    private String username;

    @Schema(description = "Email of the account", example = "filip@gmail.com")
    private String email;

    @Schema(description = "Account number", example = "123456789")
    private String accountNumber;

    @Schema(description = "Member ranking of the account", example = "STANDARD")
    private MemberRanking memberRanking=MemberRanking.STANDARD;

    @Schema(description = "Balance of the account", example = "1000.00")
    private BigDecimal balance=BigDecimal.ZERO;
}

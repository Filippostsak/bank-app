package com.microservices.accountservice.dto;

import com.microservices.accountservice.enums.MemberRanking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Account read only data transfer object")
public class AccountReadOnlyDTO {

    @Schema(description = "Account id", example = "1")
    private Long id;

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

    @Schema(description = "Created date of the entity", example = "2021-07-01T10:00:00")
    private LocalDateTime createdDate;

    @Schema(description = "Updated date of the entity", example = "2021-07-01T10:00:00")
    private LocalDateTime updatedDate;

    @Schema(description = "Created by", example = "admin")
    private String createdBy;

    @Schema(description = "Updated by", example = "admin")
    private String updatedBy;

    @Schema(description = "Is active", example = "true")
    private boolean isActive;

}

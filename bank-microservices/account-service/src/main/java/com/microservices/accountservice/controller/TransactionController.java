package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.TransactionDTO;
import com.microservices.accountservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing transaction-related operations.
 */
@RestController
@RequestMapping("/api/v1/transaction")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Adds money to the account.
     *
     * @param dto The transaction details for adding money.
     * @return ResponseEntity with HTTP status OK if the transaction is successful.
     */
    @Operation(summary = "Add money to account", description = "Adds a specified amount of money to the account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money added successfully"),
            @ApiResponse(responseCode = "400", description = "Limit exceeded or account not found")
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addMoney(@RequestBody TransactionDTO dto) {
        transactionService.addMoney(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Withdraws money from the account.
     *
     * @param dto The transaction details for withdrawing money.
     * @return ResponseEntity with HTTP status OK if the transaction is successful.
     */
    @Operation(summary = "Withdraw money from account", description = "Withdraws a specified amount of money from the account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money withdrawn successfully"),
            @ApiResponse(responseCode = "400", description = "Limit exceeded, insufficient balance, or account not found")
    })
    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawMoney(@RequestBody TransactionDTO dto) {
        transactionService.withdrawMoney(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

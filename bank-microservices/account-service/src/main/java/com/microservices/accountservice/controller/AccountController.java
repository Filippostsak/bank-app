package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.*;
import com.microservices.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for managing account-related operations.
 */
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    /**
     * Gets the current logged-in user details.
     *
     * @return ResponseEntity containing the current logged-in user details.
     */
    @Operation(summary = "Get current logged-in user details", description = "Fetches the details of the currently logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details fetched successfully")
    })
    @GetMapping("/get")
    public ResponseEntity<UserGetCurrentLoggedInUserDTO> getUser() {
        UserGetCurrentLoggedInUserDTO userGetCurrentLoggedInUserDTO = accountService.getCurrentLoggedInUser();
        return ResponseEntity.ok(userGetCurrentLoggedInUserDTO);
    }

    /**
     * Creates a new account.
     *
     * @return ResponseEntity containing the created account details.
     */
    @Operation(summary = "Create a new account", description = "Creates a new account for the current logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Account already exists")
    })
    @PostMapping("/create")
    public ResponseEntity<AccountReadOnlyDTO> createAccount() {
        AccountReadOnlyDTO accountReadOnlyDTO = accountService.createAccount();
        return new ResponseEntity<>(accountReadOnlyDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing account.
     *
     * @param dto The account update details.
     * @return ResponseEntity containing the updated account details.
     */
    @Operation(summary = "Update an account", description = "Updates the details of an existing account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping("/update")
    public ResponseEntity<AccountReadOnlyDTO> updateAccount(@Valid @RequestBody AccountUpdateDTO dto) {
        AccountReadOnlyDTO accountReadOnlyDTO = accountService.updateAccount(dto);
        return new ResponseEntity<>(accountReadOnlyDTO, HttpStatus.OK);
    }

    /**
     * Deletes an account.
     *
     * @return ResponseEntity containing the details of the deleted account.
     */
    @Operation(summary = "Delete an account", description = "Deletes an existing account of the current logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<AccountDeleteDTO> deleteAccount() {
        AccountDeleteDTO accountDeleteDTO = accountService.deleteAccount();
        return new ResponseEntity<>(accountDeleteDTO, HttpStatus.OK);
    }

    /**
     * Gets the account ID of the current logged-in user.
     *
     * @return ResponseEntity containing the account ID details.
     */
    @Operation(summary = "Get current user account ID", description = "Fetches the account ID of the currently logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account ID fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/find/accountId")
    public ResponseEntity<Optional<AccountFindAccountIdDTO>> getCurrentUserAccountId() {
        Optional<AccountFindAccountIdDTO> accountFindAccountIdDTO = accountService.findAccountId();
        return new ResponseEntity<>(accountFindAccountIdDTO, HttpStatus.OK);
    }
}

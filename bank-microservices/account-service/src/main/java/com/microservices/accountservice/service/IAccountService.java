package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.AccountDeleteDTO;
import com.microservices.accountservice.dto.AccountFindAccountIdDTO;
import com.microservices.accountservice.dto.AccountReadOnlyDTO;
import com.microservices.accountservice.dto.AccountUpdateDTO;
import com.microservices.accountservice.exception.AccountAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

/**
 * Service interface for managing account-related operations.
 */
public interface IAccountService {

    /**
     * Creates a new account for the current user.
     *
     * @return the created account as a read-only DTO.
     * @throws AccountAlreadyExistsException if the account already exists.
     */
    AccountReadOnlyDTO createAccount() throws AccountAlreadyExistsException;

    /**
     * Updates an existing account with the provided details.
     *
     * @param dto the account update DTO containing the updated details.
     * @return the updated account as a read-only DTO.
     * @throws RuntimeException if the account does not exist.
     */
    AccountReadOnlyDTO updateAccount(AccountUpdateDTO dto) throws RuntimeException;

    /**
     * Deletes the current user's account.
     *
     * @return the account delete DTO containing details of the deleted account.
     * @throws EntityNotFoundException if the account does not exist.
     */
    AccountDeleteDTO deleteAccount() throws EntityNotFoundException;

    /**
     * Finds the account ID for the current user.
     *
     * @return an optional containing the account ID DTO if found, or an empty optional if not found.
     * @throws EntityNotFoundException if the account does not exist.
     */
    Optional<AccountFindAccountIdDTO> findAccountId() throws EntityNotFoundException;
}

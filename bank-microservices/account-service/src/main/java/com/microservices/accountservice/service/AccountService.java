package com.microservices.accountservice.service;

import com.microservices.accountservice.client.UserClient;
import com.microservices.accountservice.dto.*;
import com.microservices.accountservice.exception.AccountAlreadyExistsException;
import com.microservices.accountservice.mapper.AccountMapper;
import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserClient userClient;

    @Override
    public AccountReadOnlyDTO createAccount() throws AccountAlreadyExistsException {
        // Fetch current user details
        log.info("Fetching current user details");
        UserGetCurrentLoggedInUserDTO currentUser = getCurrentLoggedInUser();
        log.info("Current user details fetched successfully");
        String username = currentUser.getUsername();

        // Check if the account already exists
        log.info("Checking if account already exists");
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        log.info("Account checked successfully");
        if (existingAccount.isPresent()) {
            throw new AccountAlreadyExistsException("Account already exists");
        }

        // Create AccountCreateDTO with current user's details
        log.info("Creating AccountCreateDTO");
        AccountCreateDTO accountCreateDTO = new AccountCreateDTO();
        accountCreateDTO.setUsername(currentUser.getUsername());
        accountCreateDTO.setEmail(currentUser.getEmail());
        accountCreateDTO.setAccountNumber(generateAccountNumber());

        // Map AccountCreateDTO to Account entity
        log.info("Mapping AccountCreateDTO to Account entity");
        Account newAccount = accountMapper.toEntity(accountCreateDTO);

        // Save the new account to the repository
        log.info("Saving the new account to the repository");
        Account savedAccount = accountRepository.save(newAccount);

        // Return the saved account as AccountReadOnlyDTO
        log.info("Returning the saved account as AccountReadOnlyDTO");
        return accountMapper.toAccountReadOnlyDTO(savedAccount);
    }

    @Override
    public AccountReadOnlyDTO updateAccount(AccountUpdateDTO dto) throws RuntimeException {
        log.info("Fetching current logged-in user details");
        UserGetCurrentLoggedInUserDTO currentUser = getCurrentLoggedInUser();
        String username = currentUser.getUsername();

        log.info("Finding existing account by username: {}", username);
        Optional<Account> existingAccountOpt = accountRepository.findByUsername(username);
        if (existingAccountOpt.isEmpty()) {
            throw new RuntimeException("Account does not exist");
        }

        Account existingAccount = existingAccountOpt.get();

        boolean isUpdated = false;
        if (dto.getUsername() != null && !dto.getUsername().equals(existingAccount.getUsername())) {
            existingAccount.setUsername(dto.getUsername());
            isUpdated = true;
        }
        if (dto.getEmail() != null && !dto.getEmail().equals(existingAccount.getEmail())) {
            existingAccount.setEmail(dto.getEmail());
            isUpdated = true;
        }

        log.info("Saving updated account");
        Account savedAccount = accountRepository.save(existingAccount);

        if (isUpdated) {
            log.info("Updating user details in user-service");
            UserUpdateDTO userUpdateDTO = new UserUpdateDTO(dto.getUsername(), dto.getEmail());
            userClient.updateUser(userUpdateDTO);
        }

        return accountMapper.toAccountReadOnlyDTO(savedAccount);
    }

    @Override
    public AccountDeleteDTO deleteAccount() throws EntityNotFoundException {
        log.info("Fetching current logged-in user details");
        UserGetCurrentLoggedInUserDTO currentUser = getCurrentLoggedInUser();
        String username = currentUser.getUsername();

        log.info("Finding existing account by username: {}", username);
        Optional<Account> existingAccountOpt = accountRepository.findByUsername(username);
        if (existingAccountOpt.isEmpty()) {
            throw new EntityNotFoundException("Account does not exist");
        }

        log.info("Deleting user in user-service");
        UserDeleteDTO userDeleteDTO = userClient.deleteUser();
        log.info("User deleted successfully");
        Account existingAccount = existingAccountOpt.get();

        userDeleteDTO.setMessage("User deleted successfully");

        log.info("Deleting account");
        accountRepository.delete(existingAccount);

        log.info("Returning AccountDeleteDTO");
        return accountMapper.toAccountDeleteDTO(existingAccount);
    }

    public UserGetCurrentLoggedInUserDTO getCurrentLoggedInUser() {
        log.info("Fetching current logged in user");
        return userClient.getCurrentLoggedInUser();
    }


    // Method to generate a new account number
    private String generateAccountNumber() {
        log.info("Generating a new account number");
        Random random = new Random();
        //if the number already exists, it will generate a new one
        if (accountRepository.existsByAccountNumber(String.valueOf(random.nextInt(1000000000)))) {
            log.info("Account number already exists, generating a new one");
            return generateAccountNumber();
        }
        log.info("Account number generated successfully");
        return String.valueOf(random.nextInt(1000000000));
    }
}

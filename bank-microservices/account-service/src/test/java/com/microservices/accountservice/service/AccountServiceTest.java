package com.microservices.accountservice.service;

import com.microservices.accountservice.client.UserClient;
import com.microservices.accountservice.dto.*;
import com.microservices.accountservice.exception.AccountAlreadyExistsException;
import com.microservices.accountservice.mapper.AccountMapper;
import com.microservices.accountservice.model.Account;
import com.microservices.accountservice.model.Limits;
import com.microservices.accountservice.repository.AccountRepository;
import com.microservices.accountservice.repository.LimitsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private LimitsRepository limitsRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private AccountService accountService;

    private UserGetCurrentLoggedInUserDTO mockCurrentUser;

    @BeforeEach
    void setUp() {
        mockCurrentUser = new UserGetCurrentLoggedInUserDTO();
        mockCurrentUser.setUsername("testuser");
        mockCurrentUser.setEmail("testuser@example.com");
    }

    @Test
    void testCreateAccount_Success() throws AccountAlreadyExistsException {
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());
        when(accountMapper.toEntity(any(AccountCreateDTO.class))).thenReturn(new Account());
        when(accountMapper.toAccountReadOnlyDTO(any(Account.class))).thenReturn(new AccountReadOnlyDTO());

        AccountReadOnlyDTO result = accountService.createAccount();

        assertNotNull(result);
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(limitsRepository, times(1)).save(any(Limits.class));
    }

    @Test
    void testCreateAccount_AccountAlreadyExists() {
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(new Account()));

        assertThrows(AccountAlreadyExistsException.class, () -> accountService.createAccount());
        verify(accountRepository, never()).save(any(Account.class));
        verify(limitsRepository, never()).save(any(Limits.class));
    }

    @Test
    void testUpdateAccount_Success() {
        Account existingAccount = new Account();
        existingAccount.setUsername("testuser");
        existingAccount.setEmail("testuser@example.com");

        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);
        when(accountMapper.toAccountReadOnlyDTO(any(Account.class))).thenReturn(new AccountReadOnlyDTO());

        AccountUpdateDTO updateDTO = new AccountUpdateDTO();
        updateDTO.setUsername("newusername");
        updateDTO.setEmail("newemail@example.com");

        AccountReadOnlyDTO result = accountService.updateAccount(updateDTO);

        assertNotNull(result);
        assertEquals("newusername", existingAccount.getUsername());
        assertEquals("newemail@example.com", existingAccount.getEmail());
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void testUpdateAccount_AccountNotFound() {
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        AccountUpdateDTO updateDTO = new AccountUpdateDTO();

        assertThrows(RuntimeException.class, () -> accountService.updateAccount(updateDTO));
        verify(accountRepository, never()).save(any(Account.class));
    }

    void deleteAccount_WhenAccountExists_DeletesSuccessfully() throws EntityNotFoundException {
        // Setup
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(new Account()));
        when(userClient.deleteUser()).thenReturn(new UserDeleteDTO());

        // Execute
        AccountDeleteDTO result = accountService.deleteAccount();

        // Verify
        assertNotNull(result);
        assertEquals("User deleted successfully", result.getMessage());
        verify(accountRepository, times(1)).delete(any(Account.class));
    }

    void deleteAccount_WhenAccountDoesNotExist_ThrowsEntityNotFoundException() {
        // Setup
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Execute & Verify
        assertThrows(EntityNotFoundException.class, () -> accountService.deleteAccount());
        verify(accountRepository, never()).delete(any(Account.class));
    }

    void deleteAccount_WhenUserClientDeleteFails_EnsuresAccountNotDeleted() {
        // Setup
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(new Account()));
        doThrow(new RuntimeException("User service unavailable")).when(userClient).deleteUser();

        // Execute & Verify
        assertThrows(RuntimeException.class, () -> accountService.deleteAccount());
        verify(accountRepository, never()).delete(any(Account.class));
    }


    @Test
    void testDeleteAccount_AccountNotFound() {
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.deleteAccount());
        verify(accountRepository, never()).delete(any(Account.class));
    }

    @Test
    void testFindAccountId_Success() {
        Account existingAccount = new Account();
        existingAccount.setUsername("testuser");

        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(existingAccount));
        when(accountMapper.toAccountFindAccountIdDTO(any(Account.class))).thenReturn(new AccountFindAccountIdDTO());

        Optional<AccountFindAccountIdDTO> result = accountService.findAccountId();

        assertTrue(result.isPresent());
    }

    @Test
    void testFindAccountId_AccountNotFound() {
        when(userClient.getCurrentLoggedInUser()).thenReturn(mockCurrentUser);
        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.findAccountId());
    }
}

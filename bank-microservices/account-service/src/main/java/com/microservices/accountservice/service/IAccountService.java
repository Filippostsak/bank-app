package com.microservices.accountservice.service;

import com.microservices.accountservice.dto.AccountDeleteDTO;
import com.microservices.accountservice.dto.AccountReadOnlyDTO;
import com.microservices.accountservice.dto.AccountUpdateDTO;
import com.microservices.accountservice.exception.AccountAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

public interface IAccountService {

    AccountReadOnlyDTO createAccount() throws AccountAlreadyExistsException;

    AccountReadOnlyDTO updateAccount(AccountUpdateDTO dto) throws RuntimeException;

    AccountDeleteDTO deleteAccount() throws EntityNotFoundException;
}

package com.microservices.accountservice.mapper;

import com.microservices.accountservice.dto.*;
import com.microservices.accountservice.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountCreateDTO toAccountCreateDTO(Account account);

    AccountReadOnlyDTO toAccountReadOnlyDTO(Account account);

    Account toEntity(AccountCreateDTO accountCreateDTO);

    AccountUpdateDTO toAccountUpdateDTO(Account account);

    Account toAccount(AccountUpdateDTO accountUpdateDTO);


    AccountDeleteDTO toAccountDeleteDTO(Account existingAccount);
}

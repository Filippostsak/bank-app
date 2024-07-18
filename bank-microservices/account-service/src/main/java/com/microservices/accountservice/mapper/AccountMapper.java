package com.microservices.accountservice.mapper;

import com.microservices.accountservice.dto.*;
import com.microservices.accountservice.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Account entities and various DTOs.
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Converts an Account entity to an AccountCreateDTO.
     *
     * @param account the Account entity to convert.
     * @return the converted AccountCreateDTO.
     */
    AccountCreateDTO toAccountCreateDTO(Account account);

    /**
     * Converts an Account entity to an AccountReadOnlyDTO.
     *
     * @param account the Account entity to convert.
     * @return the converted AccountReadOnlyDTO.
     */
    AccountReadOnlyDTO toAccountReadOnlyDTO(Account account);

    /**
     * Converts an AccountCreateDTO to an Account entity.
     * Sets the default MemberRanking if not provided.
     *
     * @param accountCreateDTO the AccountCreateDTO to convert.
     * @return the converted Account entity.
     */
    @Mapping(target = "memberRanking", expression = "java(accountCreateDTO.getMemberRanking() != null ? accountCreateDTO.getMemberRanking() : com.microservices.accountservice.enums.MemberRanking.STANDARD)")
    Account toEntity(AccountCreateDTO accountCreateDTO);

    /**
     * Converts an Account entity to an AccountUpdateDTO.
     *
     * @param account the Account entity to convert.
     * @return the converted AccountUpdateDTO.
     */
    AccountUpdateDTO toAccountUpdateDTO(Account account);

    /**
     * Converts an AccountUpdateDTO to an Account entity.
     *
     * @param accountUpdateDTO the AccountUpdateDTO to convert.
     * @return the converted Account entity.
     */
    Account toAccount(AccountUpdateDTO accountUpdateDTO);

    /**
     * Converts an Account entity to an AccountDeleteDTO.
     *
     * @param existingAccount the Account entity to convert.
     * @return the converted AccountDeleteDTO.
     */
    AccountDeleteDTO toAccountDeleteDTO(Account existingAccount);

    /**
     * Converts an Account entity to an AccountFindAccountIdDTO.
     *
     * @param account the Account entity to convert.
     * @return the converted AccountFindAccountIdDTO.
     */
    AccountFindAccountIdDTO toAccountFindAccountIdDTO(Account account);
}

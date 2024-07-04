package com.microservices.userservice.service;

import com.microservices.userservice.dto.UserCreateDTO;
import com.microservices.userservice.dto.UserDeleteDTO;
import com.microservices.userservice.dto.UserReadOnlyDTO;
import com.microservices.userservice.dto.UserUpdateDTO;
import com.microservices.userservice.exception.EmailAlreadyExistsException;
import com.microservices.userservice.exception.IdAlreadyExistsException;
import com.microservices.userservice.exception.PasswordIsNotConfirmedException;
import com.microservices.userservice.exception.UsernameAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;

/**
 * User Service Interface
 */

public interface IUserService {

    /**
     * Create a new user
     * @param userCreateDTO UserCreateDTO
     * @return UserReadOnlyDTO
     */

    UserReadOnlyDTO createUser(UserCreateDTO userCreateDTO)throws EmailAlreadyExistsException, UsernameAlreadyExistsException, IdAlreadyExistsException, PasswordIsNotConfirmedException;

    /**
     * Update the current user
     * @return UserReadOnlyDTO
     */

    UserReadOnlyDTO updateUser(UserUpdateDTO dto) throws EntityNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException, IdAlreadyExistsException, PasswordIsNotConfirmedException;

    /**
     * Delete the current user
     * @return String message of the deleted user
     */

    UserDeleteDTO deleteUser() throws EntityNotFoundException;
}

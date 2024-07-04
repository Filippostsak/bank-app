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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {

    UserReadOnlyDTO createUser(UserCreateDTO userCreateDTO)throws EmailAlreadyExistsException, UsernameAlreadyExistsException, IdAlreadyExistsException, PasswordIsNotConfirmedException;

    UserReadOnlyDTO updateUser(UserUpdateDTO dto) throws EntityNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException, IdAlreadyExistsException, PasswordIsNotConfirmedException;

    UserDeleteDTO deleteUser() throws EntityNotFoundException;
}

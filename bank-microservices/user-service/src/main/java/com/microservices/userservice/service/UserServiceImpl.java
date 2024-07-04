package com.microservices.userservice.service;

import com.microservices.userservice.dto.UserCreateDTO;
import com.microservices.userservice.dto.UserDeleteDTO;
import com.microservices.userservice.dto.UserReadOnlyDTO;
import com.microservices.userservice.dto.UserUpdateDTO;
import com.microservices.userservice.exception.EmailAlreadyExistsException;
import com.microservices.userservice.exception.IdAlreadyExistsException;
import com.microservices.userservice.exception.PasswordIsNotConfirmedException;
import com.microservices.userservice.exception.UsernameAlreadyExistsException;
import com.microservices.userservice.mapper.UserMapper;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserReadOnlyDTO createUser(UserCreateDTO userCreateDTO) {

        // Check if email exists
        if(userRepository.existsByEmail(userCreateDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Check if username exists
        if (userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByIdNumber(userCreateDTO.getIdNumber())) {
            throw new IdAlreadyExistsException("ID Number already exists");
        }

        //if password != confirmPassword
        if (!userCreateDTO.getPassword().equals(userCreateDTO.getConfirmPassword())) {
            throw new PasswordIsNotConfirmedException("Passwords do not match");
        }

        // Map DTO to Entity
        User user = userMapper.toUser(userCreateDTO);

        // Encode the password and set it on the user entity
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        // Save the user to the database
        user = userRepository.save(user);

        // Map Entity to ReadOnly DTO
        UserReadOnlyDTO userReadOnlyDTO = userMapper.toUserReadOnlyDTO(user);

        // Log the creation
        log.info("User created with ID: {}", user.getId());

        return userReadOnlyDTO;
    }

    @Override
    public UserReadOnlyDTO updateUser(UserUpdateDTO dto) throws EntityNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException, IdAlreadyExistsException, PasswordIsNotConfirmedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName).orElseThrow(EntityNotFoundException::new);

        // Check if email is being updated and if it already exists
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Check if username is being updated and if it already exists
        if (!user.getUsername().equals(dto.getUsername()) && userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        // Check if ID number is being updated and if it already exists
        if (dto.getIdNumber() != null && !dto.getIdNumber().equals(user.getIdNumber()) && userRepository.existsByIdNumber(dto.getIdNumber())) {
            throw new IdAlreadyExistsException("ID Number already exists");
        }

        // If password != confirmPassword
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordIsNotConfirmedException("Passwords do not match");
        }

        // Update user details using the mapper
        userMapper.updateUserFromDto(dto, user);

        // Encode the password if it's being changed
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // Save updated user to the database
        user = userRepository.save(user);

        // Map Entity to ReadOnly DTO
        UserReadOnlyDTO userReadOnlyDTO = userMapper.toUserReadOnlyDTO(user);

        // Log the update
        log.info("User updated with ID: {}", user.getId());

        return userReadOnlyDTO;
    }


    @Override
    public UserDeleteDTO deleteUser() throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findByUsername(currentPrincipalName).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
        return userMapper.toUserDeleteDTO(user);
    }
}
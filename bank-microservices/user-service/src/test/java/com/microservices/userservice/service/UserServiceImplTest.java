package com.microservices.userservice.service;

import com.microservices.userservice.dto.UserCreateDTO;
import com.microservices.userservice.dto.UserReadOnlyDTO;
import com.microservices.userservice.dto.UserUpdateDTO;
import com.microservices.userservice.enums.Role;
import com.microservices.userservice.exception.EmailAlreadyExistsException;
import com.microservices.userservice.exception.IdAlreadyExistsException;
import com.microservices.userservice.exception.PasswordIsNotConfirmedException;
import com.microservices.userservice.exception.UsernameAlreadyExistsException;
import com.microservices.userservice.mapper.UserMapper;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        // Given
        UserCreateDTO userCreateDTO = new UserCreateDTO(
                "johndoe", "John", "Doe", "john.doe@example.com", "13/05/1995",
                "123456789", "1234567890", "Password@123", "Password@123",
                Role.USER, "USA", "New York", "123 Main St",
                "12345", "Software Engineer", "Acme Corp",
                "456 Elm St", "67890", "Los Angeles", "USA",
                "0987654321", "contact@acme.com"
        );

        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("encodedPassword");
        user.setDateOfBirth(LocalDate.parse("13/05/1995", DATE_FORMATTER));

        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "encodedPassword");
        when(userMapper.toUser(any(UserCreateDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserReadOnlyDTO(any(User.class))).thenReturn(new UserReadOnlyDTO());

        // When
        UserReadOnlyDTO result = userService.createUser(userCreateDTO);

        // Then
        assertNotNull(result);
        verify(passwordEncoder).encode("Password@123");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals("encodedPassword", userArgumentCaptor.getValue().getPassword());
    }

    @Test
    void createUser_EmailAlreadyExists() {
        // Given
        UserCreateDTO userCreateDTO = new UserCreateDTO(
                "johndoe", "John", "Doe", "john.doe@example.com", "13/05/1995",
                "123456789", "1234567890", "Password@123", "Password@123",
                Role.USER, "USA", "New York", "123 Main St",
                "12345", "Software Engineer", "Acme Corp",
                "456 Elm St", "67890", "Los Angeles", "USA",
                "0987654321", "contact@acme.com"
        );

        when(userRepository.existsByEmail(userCreateDTO.getEmail())).thenReturn(true);

        // When & Then
        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.createUser(userCreateDTO)
        );

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void createUser_UsernameAlreadyExists() {
        // Given
        UserCreateDTO userCreateDTO = new UserCreateDTO(
                "johndoe", "John", "Doe", "john.doe@example.com", "13/05/1995",
                "123456789", "1234567890", "Password@123", "Password@123",
                Role.USER, "USA", "New York", "123 Main St",
                "12345", "Software Engineer", "Acme Corp",
                "456 Elm St", "67890", "Los Angeles", "USA",
                "0987654321", "contact@acme.com"
        );

        when(userRepository.existsByUsername(userCreateDTO.getUsername())).thenReturn(true);

        // When & Then
        UsernameAlreadyExistsException exception = assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userService.createUser(userCreateDTO)
        );

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void updateUser() throws EntityNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException, IdAlreadyExistsException, PasswordIsNotConfirmedException {
        // Given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("johndoe");
        SecurityContextHolder.setContext(securityContext);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("johndoe");
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("encodedPassword");
        existingUser.setDateOfBirth(LocalDate.parse("13/05/1995", DATE_FORMATTER));

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(
                "johndoe", "John", "Doe", "john.new@example.com", "13/05/1995",
                "newIdNumber", "0987654321", "NewPassword@123", "NewPassword@123",
                Role.USER, "USA", "New York", "123 Main St",
                "12345", "Software Engineer", "Acme Corp",
                "456 Elm St", "67890", "Los Angeles", "USA",
                "1234567890", "new.contact@acme.com"
        );

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("johndoe");
        updatedUser.setFirstname("John");
        updatedUser.setLastname("Doe");
        updatedUser.setEmail("john.new@example.com");
        updatedUser.setPassword("encodedNewPassword");
        updatedUser.setDateOfBirth(LocalDate.parse("13/05/1995", DATE_FORMATTER));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toUserReadOnlyDTO(any(User.class))).thenReturn(new UserReadOnlyDTO());

        // Mock the updateUserFromDto method in the userMapper
        doAnswer(invocation -> {
            UserUpdateDTO dto = invocation.getArgument(0);
            User user = invocation.getArgument(1);
            user.setUsername(dto.getUsername());
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setEmail(dto.getEmail());
            user.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth(), DATE_FORMATTER));
            user.setIdNumber(dto.getIdNumber());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setPassword(dto.getPassword());
            user.setRole(dto.getRole());
            user.setCountry(dto.getCountry());
            user.setCity(dto.getCity());
            user.setAddress(dto.getAddress());
            user.setPostalCode(dto.getPostalCode());
            user.setOccupation(dto.getOccupation());
            user.setCompany(dto.getCompany());
            user.setCompanyAddress(dto.getCompanyAddress());
            user.setCompanyPostalCode(dto.getCompanyPostalCode());
            user.setCompanyCity(dto.getCompanyCity());
            user.setCompanyCountry(dto.getCompanyCountry());
            user.setCompanyPhoneNumber(dto.getCompanyPhoneNumber());
            user.setCompanyEmail(dto.getCompanyEmail());
            return null;
        }).when(userMapper).updateUserFromDto(any(UserUpdateDTO.class), any(User.class));

        // When
        UserReadOnlyDTO result = userService.updateUser(userUpdateDTO);

        // Then
        assertNotNull(result);
        verify(passwordEncoder).encode("NewPassword@123");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        assertEquals("encodedNewPassword", userArgumentCaptor.getValue().getPassword());
        assertEquals("john.new@example.com", userArgumentCaptor.getValue().getEmail());
    }


    @Test
    void updateUser_EmailAlreadyExists() {
        // Given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("johndoe");
        SecurityContextHolder.setContext(securityContext);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("johndoe");
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("encodedPassword");
        existingUser.setDateOfBirth(LocalDate.parse("13/05/1995", DATE_FORMATTER));

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(
                "johndoe", "John", "Doe", "existing.email@example.com", "13/05/1995",
                "newIdNumber", "0987654321", "NewPassword@123", "NewPassword@123",
                Role.USER, "USA", "New York", "123 Main St",
                "12345", "Software Engineer", "Acme Corp",
                "456 Elm St", "67890", "Los Angeles", "USA",
                "1234567890", "new.contact@acme.com"
        );

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(userUpdateDTO.getEmail())).thenReturn(true);

        // When & Then
        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> userService.updateUser(userUpdateDTO)
        );

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void updateUser_UsernameAlreadyExists() {
        // Given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("johndoe");
        SecurityContextHolder.setContext(securityContext);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("johndoe");
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("encodedPassword");
        existingUser.setDateOfBirth(LocalDate.parse("13/05/1995", DATE_FORMATTER));

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(
                "newUsername", "John", "Doe", "john.doe@example.com", "13/05/1995",
                "newIdNumber", "0987654321", "NewPassword@123", "NewPassword@123",
                Role.USER, "USA", "New York", "123 Main St",
                "12345", "Software Engineer", "Acme Corp",
                "456 Elm St", "67890", "Los Angeles", "USA",
                "1234567890", "new.contact@acme.com"
        );

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername(userUpdateDTO.getUsername())).thenReturn(true);

        // When & Then
        UsernameAlreadyExistsException exception = assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userService.updateUser(userUpdateDTO)
        );

        assertEquals("Username already exists", exception.getMessage());
    }



}

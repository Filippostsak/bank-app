package com.microservices.userservice.controller;

import com.microservices.userservice.dto.*;
import com.microservices.userservice.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 */

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userServiceImpl;

    /**
     * Create a new user
     * @param dto UserCreateDTO
     * @return UserReadOnlyDTO
     */
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/create")
    public ResponseEntity<UserReadOnlyDTO> createUser(@Valid @RequestBody UserCreateDTO dto){
        UserReadOnlyDTO userReadOnlyDTO = userServiceImpl.createUser(dto);
        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.CREATED);
    }

    /**
     * Update user details
     * @return UserReadOnlyDTO
     */
    @Operation(summary = "Update user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/update")
    public ResponseEntity<UserReadOnlyDTO> updateUser(@Valid @RequestBody UserUpdateDTO dto){
        UserReadOnlyDTO userReadOnlyDTO = userServiceImpl.updateUser(dto);
        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    /**
     * Update username and email
     * @return UserReadOnlyDTO
     */

    @Operation(summary = "Update username and email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Username and email updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/update/username-email")
    public ResponseEntity<UserReadOnlyDTO> updateUsernameAndEmail(@Valid @RequestBody UserUpdateUsernameAndEmailDTO dto){
        UserReadOnlyDTO userReadOnlyDTO = userServiceImpl.updateUsernameAndEmail(dto);
        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    /**
     * Delete user
     * @return UserDeleteDTO
     */
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<UserDeleteDTO> deleteUser(){
        UserDeleteDTO userDeleteDTO = userServiceImpl.deleteUser();
        return new ResponseEntity<>(userDeleteDTO, HttpStatus.OK);
    }

    /**
     * Get user details
     * @return UserReadOnlyDTO
     */

    @Operation(summary = "Get user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/get")
    public ResponseEntity<UserGetCurrentLoggedInUserDTO> getUser(){
        UserGetCurrentLoggedInUserDTO user = userServiceImpl.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

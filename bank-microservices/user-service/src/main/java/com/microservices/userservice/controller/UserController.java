package com.microservices.userservice.controller;

import com.microservices.userservice.dto.UserCreateDTO;
import com.microservices.userservice.dto.UserDeleteDTO;
import com.microservices.userservice.dto.UserReadOnlyDTO;
import com.microservices.userservice.dto.UserUpdateDTO;
import com.microservices.userservice.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<UserReadOnlyDTO> createUser(@Valid @RequestBody UserCreateDTO dto){
        UserReadOnlyDTO userReadOnlyDTO = userServiceImpl.createUser(dto);
        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserReadOnlyDTO> updateUser(@Valid @RequestBody UserUpdateDTO dto){
        UserReadOnlyDTO userReadOnlyDTO = userServiceImpl.updateUser(dto);
        return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserDeleteDTO> deleteUser(){
        UserDeleteDTO userDeleteDTO = userServiceImpl.deleteUser();
        return new ResponseEntity<>(userDeleteDTO, HttpStatus.OK);
    }

}

package com.microservices.accountservice.controller;

import com.microservices.accountservice.dto.AccountDeleteDTO;
import com.microservices.accountservice.dto.AccountReadOnlyDTO;
import com.microservices.accountservice.dto.AccountUpdateDTO;
import com.microservices.accountservice.dto.UserGetCurrentLoggedInUserDTO;
import com.microservices.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get")
    public ResponseEntity<UserGetCurrentLoggedInUserDTO> getUser (){
        UserGetCurrentLoggedInUserDTO userGetCurrentLoggedInUserDTO = accountService.getCurrentLoggedInUser();
        return ResponseEntity.ok(userGetCurrentLoggedInUserDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountReadOnlyDTO> createAccount(){
        AccountReadOnlyDTO accountReadOnlyDTO = accountService.createAccount();
        return new ResponseEntity<>(accountReadOnlyDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountReadOnlyDTO> updateAccount(@Valid @RequestBody AccountUpdateDTO dto){
        AccountReadOnlyDTO accountReadOnlyDTO = accountService.updateAccount(dto);
        return new ResponseEntity<>(accountReadOnlyDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<AccountDeleteDTO> deleteAccount(){
        AccountDeleteDTO accountDeleteDTO = accountService.deleteAccount();
        return new ResponseEntity<>(accountDeleteDTO, HttpStatus.OK);
    }
}

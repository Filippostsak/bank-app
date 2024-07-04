package com.microservices.accountservice.client;

import com.microservices.accountservice.dto.UserDeleteDTO;
import com.microservices.accountservice.dto.UserGetCurrentLoggedInUserDTO;
import com.microservices.accountservice.dto.UserUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "http://localhost:8085/api/v1/users", configuration = FeignClientConfiguration.class)
public interface UserClient {

    @GetMapping("/get")
    UserGetCurrentLoggedInUserDTO getCurrentLoggedInUser();

    @PutMapping("/update/username-email")
    void updateUser(@Valid @RequestBody UserUpdateDTO dto);

    @DeleteMapping("/delete")
    UserDeleteDTO deleteUser();
}

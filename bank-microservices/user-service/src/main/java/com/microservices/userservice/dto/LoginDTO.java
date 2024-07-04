package com.microservices.userservice.dto;

import lombok.Data;

/**
 * LoginDTO
 */

@Data
public class LoginDTO {

    private String username;
    private String password;
}

package com.microservices.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserGetCurrentLoggedInUserDTO {

    private Long id;

    private String username;

    private String email;
}

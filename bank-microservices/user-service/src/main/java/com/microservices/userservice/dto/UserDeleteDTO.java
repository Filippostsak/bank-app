package com.microservices.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDeleteDTO {

    private Long id;

    private String message="User deleted successfully";

}

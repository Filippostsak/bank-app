package com.microservices.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDeleteDTO
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "UserDeleteDTO", description = "DTO for deleting user")
public class UserDeleteDTO {

    private Long id;

    private String message="User deleted successfully";

}

package com.microservices.userservice.dto;

import com.microservices.userservice.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserReadOnlyDTO
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "UserReadOnlyDTO", description = "DTO for reading user details")
public class UserReadOnlyDTO {

    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private String dateOfBirth;

    private String email;

    private String idNumber;

    private String phoneNumber;

    private String password;

    private Role role;

    private String country;

    private String city;

    private String address;

    private String postalCode;

    private String occupation;

    private String company;

    private String companyAddress;

    private String companyPostalCode;

    private String companyCity;

    private String companyCountry;

    private String companyPhoneNumber;

    private String companyEmail;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String createdBy;

    private String updatedBy;

    private boolean isActive;
}

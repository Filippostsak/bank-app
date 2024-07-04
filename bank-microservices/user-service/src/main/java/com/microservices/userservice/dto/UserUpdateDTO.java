package com.microservices.userservice.dto;

import com.microservices.userservice.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {

    @NotNull(message = "Username is required")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @NotNull(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstname;

    @NotNull(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastname;

    @Email(message = "Email must be a valid email address")
    @NotNull(message = "Email is required")
    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters")
    private String email;

    @NotNull(message = "Date of Birth is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Date of Birth must be in the format dd/MM/yyyy")
    private String dateOfBirth;

    @NotNull(message = "ID number is required")
    @Size(min = 2, max = 50, message = "ID number must be between 2 and 50 characters")
    private String idNumber;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?]).{8,50}$",
            message = "Password must be between 8 and 50 characters long and include at least one uppercase letter, one lowercase letter, and one symbol"
    )
    private String password;

    @NotEmpty(message = "Confirm Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?]).{8,50}$",
            message = "Password must be between 8 and 50 characters long and include at least one uppercase letter, one lowercase letter, and one symbol"
    )
    private String confirmPassword;

    @NotNull(message = "Role is required")
    private Role role = Role.USER;

    @NotNull(message = "Country is required")
    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    private String country;

    @NotNull(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @NotNull(message = "Address is required")
    @Size(min = 2, max = 100, message = "Address must be between 2 and 100 characters")
    private String address;

    @NotNull(message = "Postal code is required")
    @Pattern(regexp = "\\d{5}", message = "Postal code must be exactly 5 digits")
    private String postalCode;

    @NotNull(message = "Occupation is required")
    @Size(min = 2, max = 60, message = "Occupation must be between 2 and 60 characters")
    private String occupation;

    @NotNull(message = "Company is required")
    @Size(min = 2, max = 100, message = "Company must be between 2 and 100 characters")
    private String company;

    @NotNull(message = "Company address is required")
    @Size(min = 2, max = 100, message = "Company address must be between 2 and 100 characters")
    private String companyAddress;

    @NotNull(message = "Company postal code is required")
    @Pattern(regexp = "\\d{5}", message = "Company postal code must be exactly 5 digits")
    private String companyPostalCode;

    @NotNull(message = "Company city is required")
    @Size(min = 2, max = 50, message = "Company city must be between 2 and 50 characters")
    private String companyCity;

    @NotNull(message = "Company country is required")
    @Size(min = 2, max = 50, message = "Company country must be between 2 and 50 characters")
    private String companyCountry;

    @NotNull(message = "Company phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Company phone number must be exactly 10 digits")
    private String companyPhoneNumber;

    @Email(message = "Company email must be a valid email address")
    private String companyEmail;
}

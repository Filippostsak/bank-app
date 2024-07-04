package com.microservices.userservice.model;

import com.microservices.userservice.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

/**
 * User Entity
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "User", description = "Entity for storing user information")
public class User extends AbstractEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    @Schema(description = "Username of the user", example = "admin")
    private String username;

    @Column(nullable = false)
    @Schema(description = "First name of the user", example = "John")
    private String firstname;

    @Column(nullable = false)
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastname;

    @Column(unique = true, nullable = false)
    @Schema(description = "Email of the user", example = "fili@gmail.com")
    private String email;

    @Column(name = "identity_number", unique = true, nullable = false)
    @Schema(description = "ID number of the user", example = "1234567890")
    private String idNumber;

    @Column(name = "phone_number", nullable = false)
    @Schema(description = "Phone number of the user", example = "1234567890")
    private String phoneNumber;

    @Column(nullable = false)
    @Schema(description = "Password of the user", example = "Password123!")
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    @Schema(description = "Date of birth of the user", example = "13/05/1995")
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Role of the user", example = "ADMIN")
    private Role role;

    @Column(nullable = false)
    @Schema(description = "Country of the user", example = "South Africa")
    private String country;

    @Column(nullable = false)
    @Schema(description = "City of the user", example = "Cape Town")
    private String city;

    @Column(nullable = false)
    @Schema(description = "Address of the user", example = "123 Main Street")
    private String address;

    @Column(nullable = false)
    @Schema(description = "Postal code of the user", example = "1234")
    private String postalCode;

    @Column(nullable = false)
    @Schema(description = "Occupation of the user", example = "Developer")
    private String occupation;

    @Column(nullable = false)
    @Schema(description = "Company of the user", example = "Company")
    private String company;

    @Column(name = "company_address", nullable = false)
    @Schema(description = "Company address of the user", example = "123 Main Street")
    private String companyAddress;

    @Column(name = "company_postal_code", nullable = false)
    @Schema(description = "Company postal code of the user", example = "1234")
    private String companyPostalCode;

    @Column(name = "company_city", nullable = false)
    @Schema(description = "Company city of the user", example = "Cape Town")
    private String companyCity;

    @Column(name = "company_country", nullable = false)
    @Schema(description = "Company country of the user", example = "South Africa")
    private String companyCountry;

    @Column(name = "company_phone_number", nullable = false)
    @Schema(description = "Company phone number of the user", example = "1234567890")
    private String companyPhoneNumber;

    @Column(name = "company_email", nullable = false)
    @Schema(description = "Company email of the user", example = "talk@gmail.com")
    private String companyEmail;

    /**
     * Get the authorities of the user
     * @return Collection of GrantedAuthority
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Get the password of the user
     * @return String
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the account is locked
     * @return boolean
     */

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the credentials are expired
     * @return boolean
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user is enabled
     * @return boolean
     */

    @Override
    public boolean isEnabled() {
        return true;
    }
}

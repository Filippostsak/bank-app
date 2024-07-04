package com.microservices.userservice.mapper;

import com.microservices.userservice.dto.UserCreateDTO;
import com.microservices.userservice.dto.UserDeleteDTO;
import com.microservices.userservice.dto.UserReadOnlyDTO;
import com.microservices.userservice.dto.UserUpdateDTO;
import com.microservices.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * User Mapper
 */

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Map UserCreateDTO to User
     * @param userCreateDTO UserCreateDTO
     * @return User
     */

    @Mapping(target = "dateOfBirth", expression = "java(stringToDate(userCreateDTO.getDateOfBirth()))")
    User toUser(UserCreateDTO userCreateDTO);

    /**
     * Map User to UserCreateDTO
     * @param user User
     * @return UserCreateDTO
     */

    @Mapping(target = "dateOfBirth", expression = "java(dateToString(user.getDateOfBirth()))")
    UserCreateDTO toUserCreateDTO(User user);

    /**
     * Map User to UserReadOnlyDTO
     * @param user User
     * @return UserReadOnlyDTO
     */

    @Mapping(target = "dateOfBirth", expression = "java(dateToString(user.getDateOfBirth()))")
    UserReadOnlyDTO toUserReadOnlyDTO(User user);

    /**
     * Map UserReadOnlyDTO to User
     * @param userReadOnlyDTO UserReadOnlyDTO
     * @return User
     */

    @Mapping(target = "dateOfBirth", expression = "java(stringToDate(userReadOnlyDTO.getDateOfBirth()))")
    User toUser(UserReadOnlyDTO userReadOnlyDTO);


    /**
     * Map User to UserDeleteDTO
     * @param user User
     * @return UserDeleteDTO
     */

    UserDeleteDTO toUserDeleteDTO(User user);

    /**
     * Update User from UserUpdateDTO
     * @param userUpdateDTO UserUpdateDTO
     * @param user User
     */

    @Mapping(target = "dateOfBirth", expression = "java(stringToDate(userUpdateDTO.getDateOfBirth()))")
    void updateUserFromDto(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

    /**
     * Convert String to LocalDate
     * @param date String
     * @return LocalDate
     */

    default LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Convert LocalDate to String
     * @param date LocalDate
     * @return String
     */

    default String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}

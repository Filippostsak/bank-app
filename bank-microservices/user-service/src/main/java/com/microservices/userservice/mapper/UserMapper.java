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

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "dateOfBirth", expression = "java(stringToDate(userCreateDTO.getDateOfBirth()))")
    User toUser(UserCreateDTO userCreateDTO);

    @Mapping(target = "dateOfBirth", expression = "java(dateToString(user.getDateOfBirth()))")
    UserCreateDTO toUserCreateDTO(User user);

    @Mapping(target = "dateOfBirth", expression = "java(dateToString(user.getDateOfBirth()))")
    UserReadOnlyDTO toUserReadOnlyDTO(User user);

    @Mapping(target = "dateOfBirth", expression = "java(stringToDate(userReadOnlyDTO.getDateOfBirth()))")
    User toUser(UserReadOnlyDTO userReadOnlyDTO);


    UserDeleteDTO toUserDeleteDTO(User user);

    @Mapping(target = "dateOfBirth", expression = "java(stringToDate(userUpdateDTO.getDateOfBirth()))")
    void updateUserFromDto(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

    default LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    default String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}

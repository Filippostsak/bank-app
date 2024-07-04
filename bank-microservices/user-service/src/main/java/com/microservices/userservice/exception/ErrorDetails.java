package com.microservices.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private List<String> errors;

}

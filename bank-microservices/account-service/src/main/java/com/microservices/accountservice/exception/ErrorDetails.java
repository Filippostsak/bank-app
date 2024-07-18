package com.microservices.accountservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Error details")
public class ErrorDetails {

    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;
    @Schema(description = "Status code of the error")
    private String message;
    @Schema(description = "Details of the error")
    private String details;
    @Schema(description = "List of errors")
    private List<String> errors;

}

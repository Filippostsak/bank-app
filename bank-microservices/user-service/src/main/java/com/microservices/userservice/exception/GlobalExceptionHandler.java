package com.microservices.userservice.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "User Already Exists", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IdAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleIdAlreadyExistsException(IdAlreadyExistsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "ID Already Exists", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordIsNotConfirmedException.class)
    public final ResponseEntity<ErrorDetails> handlePasswordIsNotConfirmedException(PasswordIsNotConfirmedException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Password Is Not Confirmed", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Username Already Exists", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Email Already Exists", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Server Error", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorDetails> handleConstraintViolationExceptions(ConstraintViolationException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> details.add(violation.getMessage()));
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Validation Failed", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            details.add(fieldName + ": " + errorMessage);
        });
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Validation Failed", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<ErrorDetails> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Missing Parameters", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorDetails> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Type Mismatch", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<ErrorDetails> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "No Handler Found", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorDetails> handleNullPointerException(NullPointerException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Null Pointer Exception", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Illegal Argument Exception", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public final ResponseEntity<ErrorDetails> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Index Out Of Bounds Exception", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ErrorDetails> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMethod() + " method is not supported for this request.");
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Method Not Allowed", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public final ResponseEntity<ErrorDetails> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        details.add(builder.toString());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Unsupported Media Type", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public final ResponseEntity<ErrorDetails> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("Acceptable media types are " + ex.getSupportedMediaTypes());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Not Acceptable", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Entity Not Found", request.getDescription(false), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}

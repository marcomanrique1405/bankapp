package com.marco.bankapp.common.exception;

import com.marco.bankapp.common.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                exception.getCode(),
                exception.getMessage(),
                request.getRequestURI()

        );

        return ResponseEntity
                .status(status)
                .body(error);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(
            InvalidRequestException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                exception.getCode(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(
            ConflictException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                exception.getCode(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        FieldError fieldError = exception
                .getBindingResult()
                .getFieldError();

        String message = fieldError != null
                ? fieldError.getDefaultMessage()
                : "Validation failed";

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                "VALIDATION_ERROR",
                message,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error(
                "Unhandled exception while processing request {}",
                request.getRequestURI(),
                exception
        );

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);

    }


    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                "ACCESS_DENIED",
                "You do not have permission to access this resource",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(error);
    }


}

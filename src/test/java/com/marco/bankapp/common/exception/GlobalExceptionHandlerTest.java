package com.marco.bankapp.common.exception;

import com.marco.bankapp.common.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.file.AccessDeniedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void shouldReturn404WhenResourceIsNotFound() {

        //ARRANGE
        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/customers/123");

        ResourceNotFoundException exception =
                new ResourceNotFoundException("Customer not found");

        //ACT
        ResponseEntity<ApiError> response =
                handler.handleResourceNotFound(exception, request);

        //ASSERT
        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                404,
                response.getBody().status()
        );

        assertEquals(
                "RESOURCE_NOT_FOUND",
                response.getBody().code()
        );

        assertEquals(
                "Customer not found",
                response.getBody().message()
        );

        assertEquals(
                "/api/customers/123",
                response.getBody().path()
        );

    }


    @Test
    void shouldReturn409WhenConflictOccurs() {

        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/auth/register");

        ConflictException exception =
                new ConflictException(
                        "EMAIL_ALREADY_EXISTS",
                        "Email is already registered"
                );

        ResponseEntity<ApiError> response =
                handler.handleConflict(exception, request);

        assertEquals(
                HttpStatus.CONFLICT,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                409,
                response.getBody().status()
        );

        assertEquals(
                "EMAIL_ALREADY_EXISTS",
                response.getBody().code()
        );

        assertEquals(
                "Email is already registered",
                response.getBody().message()
        );

        assertEquals(
                "/api/auth/register",
                response.getBody().path()
        );

    }


    @Test
    void shouldReturn400WhenRequestIsInvalid() {

        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/transactions");

        InvalidRequestException exception =
                new InvalidRequestException(
                        "INVALID_TRANSFER_AMOUNT",
                        "Transfer amount must be greater than zero"
                );

        ResponseEntity<ApiError> response =
                handler.handleInvalidRequest(exception, request);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                400,
                response.getBody().status()
        );

        assertEquals(
                "INVALID_TRANSFER_AMOUNT",
                response.getBody().code()
        );

        assertEquals(
                "Transfer amount must be greater than zero",
                response.getBody().message()
        );

        assertEquals(
                "/api/transactions",
                response.getBody().path()
        );

    }


    @Test
    void shouldReturn400WhenValidationFails() {

        // ARRANGE
        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/auth/register");

        MethodArgumentNotValidException exception =
                mock(MethodArgumentNotValidException.class);

        BindingResult bindingResult =
                mock(BindingResult.class);

        FieldError fieldError =
                new FieldError(
                        "registerRequest",
                        "email",
                        "must be a valid email"
                );

        when(exception.getBindingResult())
                .thenReturn(bindingResult);

        when(bindingResult.getFieldError())
                .thenReturn(fieldError);

        // ACT
        ResponseEntity<ApiError> response =
                handler.handleValidation(exception, request);

        // ASSERT
        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                400,
                response.getBody().status()
        );

        assertEquals(
                "VALIDATION_ERROR",
                response.getBody().code()
        );

        assertEquals(
                "must be a valid email",
                response.getBody().message()
        );

        assertEquals(
                "/api/auth/register",
                response.getBody().path()
        );
    }


    @Test
    void shouldReturn403WhenAccessIsDenied() {

        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/admin/users");

        AccessDeniedException exception =
                new AccessDeniedException("Access denied");

        ResponseEntity<ApiError> response =
                handler.handleAccessDenied(
                        exception,
                        request
                );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                HttpStatus.FORBIDDEN,
                response.getStatusCode()
        );

        assertEquals(
                "ACCESS_DENIED",
                response.getBody().code()
        );

        assertEquals(
                403,
                response.getBody().status()
        );

        assertEquals(
                "You do not have permission to access this resource",
                response.getBody().message()
        );

        assertEquals(
                "/api/admin/users",
                response.getBody().path()
        );

    }


    @Test
    void shouldReturn500WhenUnexpectedExceptionOccurs() {

        HttpServletRequest request =
                mock(HttpServletRequest.class);

        when(request.getRequestURI())
                .thenReturn("/api/accounts");

        Exception exception =
                new RuntimeException("Sensitive internal database error");

        ResponseEntity<ApiError> response =
                handler.handleUnexpectedException(
                        exception,
                        request
                );

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                500,
                response.getBody().status()
        );

        assertEquals(
                "INTERNAL_SERVER_ERROR",
                response.getBody().code()
        );

        assertEquals(
                "An unexpected error occurred",
                response.getBody().message()
        );

        assertEquals(
                "/api/accounts",
                response.getBody().path()
        );

    }

}

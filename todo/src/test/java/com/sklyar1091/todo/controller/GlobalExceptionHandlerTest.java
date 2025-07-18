package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.exception.ApiError;
import com.sklyar1091.todo.exception.ApiException;
import com.sklyar1091.todo.exception.ErrorMessage;
import com.sklyar1091.todo.exception.ErrorResponse;
import com.sklyar1091.todo.exception.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getRequestURI()).thenReturn("/test-endpoint");
    }

    @Test
    @DisplayName("Handling TaskNotFoundException")
    void whenTaskNotFoundExceptionThenHandleIt() {
        ApiError apiError = new ApiError(ErrorMessage.TASK_NOT_FOUND, "Task Not found!");
        TaskNotFoundException exception = new TaskNotFoundException(apiError);

        ResponseEntity<ErrorResponse> responseEntity = handler.handleApiException(exception, request);
        ErrorResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("/test-endpoint", response.getUrl());
        assertEquals(ErrorMessage.TASK_NOT_FOUND.getCode(), response.getCode());
        assertEquals(ErrorMessage.TASK_NOT_FOUND.getMessage(), response.getMessage());
        assertEquals("Task Not found!", response.getDetails());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Handles ServerInternalError")
    void whenServerInternalErrorThenHandleIt() {
        ApiError apiError = new ApiError(ErrorMessage.INTERNAL_ERROR, "Something gone wrong");
        ApiException apiException = new ApiException(apiError);

        ResponseEntity<ErrorResponse> responseEntity = handler.handleApiException(apiException, request);
        ErrorResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("/test-endpoint", response.getUrl());
        assertEquals(ErrorMessage.INTERNAL_ERROR.getCode(), response.getCode());
        assertEquals(ErrorMessage.INTERNAL_ERROR.getMessage(), response.getMessage());
        assertEquals("Something gone wrong", response.getDetails());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Handles ConstraintsViolations")
    void whenConstraintsViolationExceptionThenHandleIt() {
        ApiError apiError = new ApiError(ErrorMessage.CONSTRAINT_VIOLATION, "Validation failed");
        ApiException apiException = new ApiException(apiError);

        ResponseEntity<ErrorResponse> responseEntity = handler.handleApiException(apiException, request);
        ErrorResponse response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("/test-endpoint", response.getUrl());
        assertEquals(ErrorMessage.CONSTRAINT_VIOLATION.getCode(), response.getCode());
        assertEquals(ErrorMessage.CONSTRAINT_VIOLATION.getMessage(), response.getMessage());
        assertEquals("Validation failed", response.getDetails());
        assertNotNull(response.getTimestamp());
    }
}
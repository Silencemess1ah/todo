package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.exception.ErrorResponse;
import com.sklyar1091.todo.exception.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        TaskNotFoundException exception = new TaskNotFoundException("Task not found");

        ErrorResponse response = handler.handleTaskNotFoundException(exception, request);

        assertEquals("/test-endpoint", response.getUrl());
        assertEquals("Task not found", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Handles ServerInternalError")
    void whenServerInternalErrorThenHandleIt() {
        Exception exception = new Exception("Internal server error");

        ErrorResponse response = handler.handleServerErrorException(exception, request);

        assertEquals("/test-endpoint", response.getUrl());
        assertEquals("Internal server error", response.getMessage());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Handles ConstraintsViolations")
    void whenConstraintsViolationExceptionThenHandleIt() {
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", null);

        ErrorResponse response = handler.handleConstraintViolationException(exception, request);

        assertEquals("/test-endpoint", response.getUrl());
        assertEquals("Validation failed", response.getMessage());
        assertNotNull(response.getTimestamp());
    }
}
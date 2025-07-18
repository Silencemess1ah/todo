package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.exception.ApiError;
import com.sklyar1091.todo.exception.ApiException;
import com.sklyar1091.todo.exception.ErrorMessage;
import com.sklyar1091.todo.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        log.error("ApiException occurred: {}", ex.getMessage(), ex);
        return handleExceptionInternal(ex, request, ex.getApiError().getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleServerErrorException(Exception e, HttpServletRequest request) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        ApiError apiError = new ApiError(
                ErrorMessage.INTERNAL_ERROR,
                "Something gone wrong");
        return handleExceptionInternal(e, request, apiError.getCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException e,
            HttpServletRequest request) {
        log.error("ConstraintViolationException occurred: {}", e.getMessage(), e);
        ApiError apiError = new ApiError(ErrorMessage.CONSTRAINT_VIOLATION, e.getMessage());
        return handleExceptionInternal(e, request, apiError.getCode());
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(
            Exception exception,
            HttpServletRequest request,
            int statusCode) {
        ApiError apiError = ((ApiException) exception).getApiError();
        return ResponseEntity
                .status(statusCode)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .url(request.getRequestURI())
                        .code(apiError.getCode())
                        .message(apiError.getMessage())
                        .details(apiError.getDetails())
                        .build());
    }
}

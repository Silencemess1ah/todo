package com.sklyar1091.todo.controller;

import com.sklyar1091.todo.exception.ErrorResponse;
import com.sklyar1091.todo.exception.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFoundException(TaskNotFoundException e, HttpServletRequest request) {
        log.error("TaskNotFoundException occurred: {}", e.getMessage(), e);
        return returnedErrorResponse(e, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerErrorException(Exception e, HttpServletRequest request) {
        log.error("Exception occurred: {}", e.getMessage(), e);
        return returnedErrorResponse(e, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e,
                                                            HttpServletRequest request) {
        log.error("ConstraintViolationException occurred: {}", e.getMessage(), e);
        return returnedErrorResponse(e, request);
    }

    private ErrorResponse returnedErrorResponse(Exception exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .url(request.getRequestURI())
                .message(exception.getMessage())
                .build();
    }
}

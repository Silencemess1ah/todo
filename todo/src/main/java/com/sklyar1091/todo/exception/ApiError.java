package com.sklyar1091.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiError {

    private final int code;
    private final String message;
    private final String details;

    public ApiError(ErrorMessage errorMessage, String details) {
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
        this.details = details;
    }
}
package com.sklyar1091.todo.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String url;
    private int code;
    private String message;
    private String details;
}

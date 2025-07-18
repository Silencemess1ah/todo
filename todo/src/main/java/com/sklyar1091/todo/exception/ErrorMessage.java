package com.sklyar1091.todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    TASK_NOT_FOUND(404, "Task does not found!"),
    INTERNAL_ERROR(500, "Internal server error!"),
    BAD_REQUEST(400, "Bad request!"),
    CONSTRAINT_VIOLATION(400, "Constraint violation!");

    private final int code;
    private final String message;
}

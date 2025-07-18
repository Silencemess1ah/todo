package com.sklyar1091.todo.exception;

public class TaskNotFoundException extends ApiException {

    public TaskNotFoundException(ApiError apiError) {
        super(apiError);
    }
}

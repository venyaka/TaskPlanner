package com.veniamin.taskplanner.exception;

import com.veniamin.taskplanner.exception.errors.NotFoundError;
import lombok.Getter;

@Getter
public class NotFoundException extends BusinessException {

    private String errorName;

    public NotFoundException(NotFoundError notFoundError) {
        super(notFoundError.getMessage());
        errorName = notFoundError.name();
    }

    public NotFoundException(String message, String errorName) {
        super(message);
        this.errorName = errorName;
    }
}

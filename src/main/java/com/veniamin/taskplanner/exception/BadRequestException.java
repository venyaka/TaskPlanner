package com.veniamin.taskplanner.exception;

import com.veniamin.taskplanner.exception.errors.BadRequestError;
import lombok.Getter;

@Getter
public class BadRequestException extends BusinessException {

    private String errorName;

    public BadRequestException(BadRequestError badRequestError) {
        super(badRequestError.getMessage());
        errorName = badRequestError.name();
    }

    public BadRequestException(String message, String errorName) {
        super(message);
        this.errorName = errorName;
    }
}

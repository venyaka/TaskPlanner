package com.veniamin.taskplanner.exception;

import com.veniamin.taskplanner.exception.errors.AuthorizedError;
import lombok.Getter;

@Getter
public class AuthorizeException extends BusinessException {

    private String errorName;

    public AuthorizeException(AuthorizedError authorizedError) {
        super(authorizedError.getMessage());
        this.errorName = authorizedError.name();
    }

    public AuthorizeException(String message, String errorName) {
        super(message);
        this.errorName = errorName;
    }
}

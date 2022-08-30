package com.revature.utils.custom_exceptions;

public class ForbiddenException extends NetworkException{
    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }
}

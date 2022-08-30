package com.revature.utils.custom_exceptions;

public class NotFoundException extends NetworkException{
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}

package com.revature.utils.custom_exceptions;

public class BadRequestException extends NetworkException{
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}

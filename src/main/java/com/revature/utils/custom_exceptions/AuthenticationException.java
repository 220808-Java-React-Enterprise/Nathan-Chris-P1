package com.revature.utils.custom_exceptions;

public class AuthenticationException extends NetworkException{
    public AuthenticationException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}

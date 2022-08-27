package com.revature.utils.custom_exceptions;

public class ResourceConflictException extends NetworkException{
    public ResourceConflictException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode(){
        return 409;
    }
}

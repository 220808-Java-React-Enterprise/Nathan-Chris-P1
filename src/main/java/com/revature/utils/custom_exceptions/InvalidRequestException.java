package com.revature.utils.custom_exceptions;

public class InvalidRequestException extends NetworkException{
    public InvalidRequestException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode(){
        return 403;
    }
}

package com.revature.dtos.requests.admin;

public class ActivateUserRequest {
    String username;

    public ActivateUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "ActivateUserRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}

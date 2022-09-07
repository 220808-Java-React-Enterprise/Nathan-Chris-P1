package com.revature.dtos.requests.admin;

public class DeactivateUserRequest{
    String username;

    public DeactivateUserRequest() {
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

package com.revature.dtos.requests.admin;

public class DeleteUserRequest {
    String username;

    public DeleteUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "DeleteUserRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}

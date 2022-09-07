package com.revature.dtos.requests.admin;

public class UpdateUserPasswordRequest{
    String username;
    String password;

    public UpdateUserPasswordRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UpdateUserPasswordRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

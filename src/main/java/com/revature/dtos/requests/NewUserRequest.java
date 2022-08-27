package com.revature.dtos.requests;

public class NewUserRequest {
    private String username;
    private String password;

    public NewUserRequest(){}

    //TODO Possibly remove this
    public NewUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

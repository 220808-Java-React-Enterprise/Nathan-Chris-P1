package com.revature.dtos.requests;

import java.util.UUID;

public class NewUserRequest {
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private String role;

    public NewUserRequest() {
    }

    public NewUserRequest(String username, String email, String password, String given_name, String surname, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

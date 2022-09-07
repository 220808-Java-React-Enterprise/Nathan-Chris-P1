package com.revature.models;

import java.util.UUID;

public class User {
    private final UUID user_id;
    private final String username;
    private final String email;
    private String password;
    private final String given_name;
    private final String surname;
    private boolean is_active;
    private final UserRole role;

    public User(UUID user_id, String username, String email, String password, String given_name, String surname, boolean is_active, UserRole role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
        this.role = role;
    }

    public UUID getUserID() {
        return user_id;
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

    public String getGivenName() {
        return given_name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isActive() {
        return is_active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void activate() {
        this.is_active = true;
    }

    public void deactivate() {
        this.is_active = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", is_active=" + is_active +
                ", role=" + role +
                '}';
    }
}

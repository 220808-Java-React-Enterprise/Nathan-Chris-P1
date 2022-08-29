package com.revature.models;

import java.util.UUID;

public class Principal {
    private String username;
    private UserRole role;

    public Principal(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "username=" + username +
                ", role=" + role +
                '}';
    }
}

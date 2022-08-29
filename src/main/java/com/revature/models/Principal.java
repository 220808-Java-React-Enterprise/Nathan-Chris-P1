package com.revature.models;

import java.util.UUID;

public class Principal {
    private String username;

    public Principal(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "username=" + username +
                '}';
    }
}

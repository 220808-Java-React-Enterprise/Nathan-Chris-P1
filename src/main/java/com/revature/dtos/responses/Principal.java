package com.revature.dtos.responses;

public class Principal {
    private String username;
    private boolean isAdmin;

    public Principal(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        return (isAdmin ? "Admin " : "") + username;
    }
}

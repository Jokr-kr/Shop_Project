package com.github.jokrkr.shopproject.server.models;

public class Session {
    private final String username;
    private final String role;

    public Session(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

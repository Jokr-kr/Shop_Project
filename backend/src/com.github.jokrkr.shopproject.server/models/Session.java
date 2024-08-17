package com.github.jokrkr.shopproject.server.models;

// Session.java (New file)
public class Session {
    private String username;
    private String role;

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
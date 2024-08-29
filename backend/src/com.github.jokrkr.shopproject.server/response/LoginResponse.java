package com.github.jokrkr.shopproject.server.response;

import org.json.JSONObject;

public class LoginResponse {
    private final int statusCode;
    private final String sessionId;
    private final String role;
    private final String message;

    private LoginResponse(int statusCode, String sessionId, String role, String message) {
        this.statusCode = statusCode;
        this.sessionId = sessionId;
        this.role = role;
        this.message = message;
    }

    public static LoginResponse success(String sessionId, String role) {
        return new LoginResponse(200, sessionId, role, "Login successful");
    }

    public static LoginResponse incorrectPassword() {
        return new LoginResponse(401, null, null, "Incorrect password");
    }

    public static LoginResponse usernameNotFound() {
        return new LoginResponse(404, null, null, "Username not found");
    }

    public static LoginResponse serverError() {
        return new LoginResponse(500, null, null, "Internal server error");
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("statusCode", statusCode);
        json.put("sessionId", sessionId);
        json.put("role", role);
        json.put("message", message);
        return json;
    }
}
package com.github.jokrkr.shopproject.server.response;

import org.json.JSONObject;

public class LoginResponse {

    private String status;
    private String message;
    private int statusCode;
    private String sessionId;

    // Constructors
    public LoginResponse(String status, String message, int statusCode) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }

    public LoginResponse(String status, String message, int statusCode, String sessionId) {
        this(status, message, statusCode); // Calls the other constructor
        this.sessionId = sessionId;
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    // Static factory methods
    public static LoginResponse success(String sessionId) {
        return new LoginResponse("SUCCESS", "Login successful", 200, sessionId);
    }

    public static LoginResponse usernameNotFound() {
        return new LoginResponse("USERNAME_NOT_FOUND", "Username does not exist", 404);
    }

    public static LoginResponse incorrectPassword() {
        return new LoginResponse("INCORRECT_PASSWORD", "Incorrect password", 401);
    }

    public static LoginResponse serverError() {
        return new LoginResponse("SERVER_ERROR", "An error occurred on the server", 500);
    }

    // Convert LoginResponse to JSON string
    public String toJson() {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", this.status);
        jsonResponse.put("message", this.message);
        jsonResponse.put("statusCode", this.statusCode);
        if (this.sessionId != null) {
            jsonResponse.put("sessionId", this.sessionId);
        }
        return jsonResponse.toString();
    }
}

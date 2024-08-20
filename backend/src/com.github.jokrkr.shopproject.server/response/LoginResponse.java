package com.github.jokrkr.shopproject.server.response;

public class LoginResponse {

    private String status;
    private String message;
    private int statusCode;
    private String sessionId;

    // Constructor for responses without sessionId
    public LoginResponse(String status, String message, int statusCode) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }

    // Constructor for responses with sessionId
    public LoginResponse(String status, String message, int statusCode, String sessionId) {
        this(status, message, statusCode); // Calls the other constructor
        this.sessionId = sessionId;
    }

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
}

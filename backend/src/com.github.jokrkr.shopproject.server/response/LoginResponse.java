package com.github.jokrkr.shopproject.server.response;

public class LoginResponse {

    private final String status;
    private final String message;
    private final int statusCode;

    public LoginResponse(String status, String message, int statusCode) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
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

    public static LoginResponse success() {
        return new LoginResponse("SUCCESS", "Login successful", 200);
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
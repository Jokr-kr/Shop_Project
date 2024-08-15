package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.services.userService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class DeleteUser implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeleteUser.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        userService userService = null;

        try {

            JsonObject requestBody = parseRequestBody(exchange);

            String adminUsername = getStringFromJson(requestBody, "adminUsername");
            String adminPassword = getStringFromJson(requestBody, "adminPassword");
            String username = getStringFromJson(requestBody, "username");

            if (adminUsername == null || adminPassword == null || username == null) {
                sendResponse(exchange, 400, "Missing required fields");
                return;
            }

            userService = new userService();

            boolean success = userService.deleteUser(adminUsername, adminPassword, username);

            if (success) {
                sendResponse(exchange, 200, "User deleted successfully");
                logger.info("User deleted successfully: {}", username);
            } else {
                sendResponse(exchange, 403, "Unauthorized action or user not found");
                logger.info("Unauthorized attempt to delete user: {}", username);
            }

        } catch (SQLException e) {
            logger.error("Database error deleting user", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (userService != null) {
                userService.close();
            }
        }
    }

    private JsonObject parseRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder rawJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                rawJson.append(line);
            }
            return JsonParser.parseString(rawJson.toString()).getAsJsonObject();
        }
    }

    private String getStringFromJson(JsonObject object, String key) {
        return object.has(key) ? object.get(key).getAsString() : null;
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (var os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

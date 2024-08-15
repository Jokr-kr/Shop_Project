package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.models.Role;
import com.github.jokrkr.shopproject.server.models.User;
import com.github.jokrkr.shopproject.server.services.userService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
public class CreateUser implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(CreateUser.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        userService userService = null;
        try {
            userService = new userService();
            User user  = parseRequestBody(exchange);
            validateUser(user);

            userService.createUser(user);
            sendResponse(exchange, 200, "User created successfully");
            logger.info("Item created successfully: {}", user.getUserName());

        } catch (JsonSyntaxException e) {
            logger.error("Invalid JSON format", e);
            sendResponse(exchange, 400, "Invalid JSON format");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input data", e);
            sendResponse(exchange, 400, "Invalid input data");
        } catch (SQLException e) {
            logger.error("Database error", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (userService != null) {
                userService.close();
            }
        }
    }

    //------------------------
    //
    private User parseRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder rawJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                rawJson.append(line);
            }
            String jsonString = rawJson.toString();

            JsonObject object = JsonParser.parseString(jsonString).getAsJsonObject();
            String userName = object.get("userName").getAsString();
            String password = object.get("password").getAsString();
            String roleString = object.get("role").getAsString();

            Role role = Role.valueOf(roleString);
            User user = new User(userName,password,role);
            logger.info("Raw JSON Input: {}", jsonString);
            return user;
        }
    }
    //------------------------
    //
    private void validateUser(User user) {
        if (user.getUserName() == null || user.getPassword() == null || user.getRole() == null){
            logger.warn("Validation failed for User: {}", user);
            throw new IllegalArgumentException("Invalid input data");
        }
    }
    //------------------------
    //
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

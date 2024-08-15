package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.Utility.ParsingUtility;
import com.github.jokrkr.shopproject.server.models.User;
import com.github.jokrkr.shopproject.server.services.userService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class CreateUser implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(CreateUser.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        User user = ParsingUtility.parseUser(exchange);
        userService userService = null;

        try {
            userService = new userService();

            validateUser(user);
            userService.createUser(user);

            sendResponse(exchange, 200, "User created successfully");
            logger.info("User created successfully: {}", user.getUserName());
        } catch (SQLException e) {
            logger.error("Database error creating user", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Error creating user", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (userService != null) {
                userService.close();
            }
        }
    }
    //------------------------
    //
    private void validateUser(User user) {
        if (user.getUserName() == null || user.getPassword() == null || user.getRole() == null) {
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

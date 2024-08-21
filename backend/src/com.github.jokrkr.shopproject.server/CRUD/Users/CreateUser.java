package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.Utility.ParsingUtility;
import com.github.jokrkr.shopproject.server.models.User;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.userService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class CreateUser implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(CreateUser.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("Handling user creation request");

        try {
            // Log the incoming request details
            logger.info("Request method: {}, URI: {}", exchange.getRequestMethod(), exchange.getRequestURI());

            // Parse user details
            User user = ParsingUtility.parseUser(exchange);
            logger.info("Parsed user: username={}, role={}", user.getUserName(), user.getRole());

            // Validate user input
            validateUser(user);
            logger.info("User validated: username={}", user.getUserName());

            // Create the user in the database
            try (userService userService = new userService()) {
                logger.info("Starting user creation in the database...");
                userService.createUser(user);
                logger.info("User creation completed for username={}", user.getUserName());
            }

            // Send success response
            ResponseUtil.sendResponse(exchange, 200, "User created successfully");
        } catch (SQLException e) {
            // Log database errors
            logger.error("Database error during user creation", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            // Log other errors
            logger.error("Error during user creation", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void validateUser(User user) {
        if (user.getUserName() == null || user.getPassword() == null || user.getRole() == null) {
            throw new IllegalArgumentException("Invalid input data");
        }
    }
}

package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.userService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.sql.SQLException;

import static com.github.jokrkr.shopproject.server.Utility.ParsingUtility.parser;

public class DeleteUser implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeleteUser.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        userService userService = null;

        try {

            JsonObject requestBody = parser(exchange);

            String adminUsername = getStringFromJson(requestBody, "adminUsername");
            String adminPassword = getStringFromJson(requestBody, "adminPassword");
            String username = getStringFromJson(requestBody, "username");

            if (adminUsername == null || adminPassword == null || username == null) {
                ResponseUtil.sendResponse(exchange, 400, "Missing required fields");
                return;
            }

            userService = new userService();

            boolean success = userService.deleteUser(adminUsername, adminPassword, username);

            if (success) {
                ResponseUtil.sendResponse(exchange, 200, "User deleted successfully");
                logger.info("User deleted successfully: {}", username);
            } else {
                ResponseUtil.sendResponse(exchange, 403, "Unauthorized action or user not found");
                logger.info("Unauthorized attempt to delete user: {}", username);
            }

        } catch (SQLException e) {
            logger.error("Database error deleting user", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (userService != null) {
                userService.close();
            }
        }
    }

    private String getStringFromJson(JsonObject object, String key) {
        return object.has(key) ? object.get(key).getAsString() : null;
    }
}

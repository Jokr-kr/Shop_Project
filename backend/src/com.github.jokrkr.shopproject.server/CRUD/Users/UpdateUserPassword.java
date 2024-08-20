package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.models.Role;
import com.github.jokrkr.shopproject.server.models.User;
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

public class UpdateUserPassword implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(UpdateUserPassword.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        userService userService = null;

        try {
            JsonObject requestBody = parser(exchange);
            String username = requestBody.has("username") ? requestBody.get("username").getAsString() : null;
            String newPassword = requestBody.has("newPassword") ? requestBody.get("newPassword").getAsString() : null;

            if (username == null || newPassword == null) {
                ResponseUtil.sendResponse(exchange, 400, "Missing required fields");
                return;
            }

            User user = new User(username,newPassword, Role.regular);

            userService = new userService();
            userService.updatePassword(user);

            ResponseUtil.sendResponse(exchange, 200, "Password updated successfully");
            logger.info("Password updated for user: {}", username);

        } catch (SQLException e) {
            logger.error("Database error updating password", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Error updating password", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (userService != null) {
                userService.close();
            }
        }
    }
}

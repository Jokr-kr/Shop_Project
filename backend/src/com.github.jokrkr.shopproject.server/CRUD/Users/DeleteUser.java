package com.github.jokrkr.shopproject.server.CRUD.Users;

import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.userService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

import static com.github.jokrkr.shopproject.server.Utility.ParsingUtility.parser;

public class DeleteUser implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(DeleteUser.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        userService userService = null;

        try {
            // Change parser to return org.json.JSONObject
            JSONObject requestBody = parser(exchange); // Assuming parser is updated to return JSONObject
            String username = requestBody.optString("username", null); // Using optString to safely get the value

            if (username == null) {
                ResponseUtil.sendResponse(exchange, 400, "Missing required fields");
                return;
            }

            userService = new userService();
            boolean success = userService.deleteUser(username);

            if (success) {
                ResponseUtil.sendResponse(exchange, 200, "User deleted successfully");
                logger.info("User deleted successfully: {}", username);
            } else {
                ResponseUtil.sendResponse(exchange, 404, "User not found");
                logger.info("Attempt to delete non-existing user: {}", username);
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
}

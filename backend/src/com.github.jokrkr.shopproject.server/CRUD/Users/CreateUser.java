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

        User user = ParsingUtility.parseUser(exchange);
        userService userService = null;

        try {
            userService = new userService();

            validateUser(user);
            userService.createUser(user);

            ResponseUtil.sendResponse(exchange, 200, "User created successfully");
            logger.info("User created successfully: {}", user.getUserName());
        } catch (SQLException e) {
            logger.error("Database error creating user", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Error creating user", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
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
 }

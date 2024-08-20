package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.Utility.ParsingUtility;
import com.github.jokrkr.shopproject.server.models.User;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.LoginService;
import com.github.jokrkr.shopproject.server.response.LoginResponse;
import com.github.jokrkr.shopproject.server.handlers.SessionHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class LogIn implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogIn.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoginService loginService = null;
        try {
            loginService = new LoginService();
            User user = ParsingUtility.parseUser(exchange);

            String username = user.getUserName();
            String password = user.getPassword();

            LoginResponse response = loginService.authenticate(username, password);

            sendResponse(exchange, response.getStatusCode(), response.getMessage());

            if (response.getStatus().equals("SUCCESS")) {
                String sessionId = SessionHandler.createSession(username, "userRole");
                logger.info("User {} logged in successfully with session ID: {}", username, sessionId);
            }

        } catch (SQLException e) {
            logger.error("Database error during login", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (loginService != null) {
                loginService.close();
            }
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        ResponseUtil.sendResponse(exchange, statusCode, response);
    }
}
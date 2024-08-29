package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.Utility.ParsingUtility;
import com.github.jokrkr.shopproject.server.models.User;
import com.github.jokrkr.shopproject.server.response.LoginResponse;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.LoginService;
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
        try {
            LoginService loginService = new LoginService();
            User user = ParsingUtility.parseUser(exchange);
            LoginResponse response = loginService.authenticate(user.getUserName(), user.getPassword());

            ResponseUtil.sendResponse(exchange, response.getStatusCode(), response.toJson());

        } catch (SQLException e) {
            logger.error("Database error during login", e);
            LoginResponse errorResponse = LoginResponse.serverError();
            ResponseUtil.sendResponse(exchange, errorResponse.getStatusCode(), errorResponse.toJson());
        }
    }
}
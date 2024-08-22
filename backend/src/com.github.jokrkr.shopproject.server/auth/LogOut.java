package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.handlers.SessionHandler;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LogOut implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogOut.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String sessionId = exchange.getRequestHeaders().getFirst("Session-ID");
        logger.info("Received logout request with Session ID: {}", sessionId);

        if (sessionId != null && SessionHandler.getSession(sessionId) != null) {
            SessionHandler.removeSession(sessionId);
            ResponseUtil.sendResponse(exchange, 200, "Logout successful");
            logger.info("Session {} successfully logged out", sessionId);
        } else {
            ResponseUtil.sendResponse(exchange, 400, "Invalid session ID");
        }
    }
}
package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.handlers.SessionHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LogOut implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogOut.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            String sessionId = exchange.getRequestHeaders().getFirst("Session-ID");

            if (sessionId != null && SessionHandler.getSession(sessionId) != null) {
                SessionHandler.removeSession(sessionId);

                sendResponse(exchange, 200, "Logout successful");
                logger.info("Session {} successfully logged out", sessionId);
            } else {
                sendResponse(exchange, 400, "Invalid session ID");
            }
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        AuthenticationUtil.sendResponse(exchange, statusCode, response);
    }
}
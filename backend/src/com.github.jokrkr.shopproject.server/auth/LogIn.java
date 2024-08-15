package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.services.LoginService;
import com.github.jokrkr.shopproject.server.response.LoginResponse;
import com.github.jokrkr.shopproject.server.session.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class LogIn implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogIn.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            LoginService loginService = null;
            try {
                loginService = new LoginService();
                JsonObject json = parseRequestBody(exchange);
                String username = json.get("username").getAsString();
                String password = json.get("password").getAsString();

                LoginResponse response = loginService.authenticate(username, password);

                sendResponse(exchange, response.getStatusCode(), response.getMessage());

                if (response.getStatus().equals("SUCCESS")) {
                    String sessionId = SessionManager.createSession(username, "userRole");
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
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private JsonObject parseRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder rawJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                rawJson.append(line);
            }
            return JsonParser.parseString(rawJson.toString()).getAsJsonObject();
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

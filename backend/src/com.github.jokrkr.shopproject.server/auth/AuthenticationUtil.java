package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.handlers.SessionHandler;
import com.github.jokrkr.shopproject.server.models.Session;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AuthenticationUtil {

    public static Session authenticate(HttpExchange exchange) throws IOException {
        String sessionId = exchange.getRequestHeaders().getFirst("Session-ID");
        Session session = SessionHandler.getSession(sessionId);

        if (session == null) {
            sendResponse(exchange, 401, "Unauthorized: Invalid or expired session");
            return null;
        }

        return session;
    }

    public static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

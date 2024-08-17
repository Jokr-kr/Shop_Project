package com.github.jokrkr.shopproject.server.auth;

import com.github.jokrkr.shopproject.server.handlers.SessionHandler;
import com.github.jokrkr.shopproject.server.models.Session;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class AuthenticationUtil {

    public static Session authenticate(HttpExchange exchange) throws IOException {
        String sessionId = exchange.getRequestHeaders().getFirst("Session-ID");
        Session session = SessionHandler.getSession(sessionId);

        if (session == null) {
            ResponseUtil.sendResponse(exchange, 401, "Unauthorized: Invalid or expired session");
            return null;
        }

        return session;
    }

}

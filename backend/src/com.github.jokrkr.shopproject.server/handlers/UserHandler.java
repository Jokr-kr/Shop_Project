package com.github.jokrkr.shopproject.server.handlers;

import com.github.jokrkr.shopproject.server.CRUD.UnsupportedMethod;
import com.github.jokrkr.shopproject.server.CRUD.Users.CreateUser;
import com.github.jokrkr.shopproject.server.CRUD.Users.DeleteUser;
import com.github.jokrkr.shopproject.server.CRUD.Users.ReadUser;
import com.github.jokrkr.shopproject.server.CRUD.Users.UpdateUserPassword;
import com.github.jokrkr.shopproject.auth.AuthenticationUtil;
import com.github.jokrkr.shopproject.server.models.Session;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserHandler implements HttpHandler {

    public void handle(HttpExchange exchange) throws IOException {

            Session session = AuthenticationUtil.authenticate(exchange);
        if (session == null) {
            return;
        }

        String method = exchange.getRequestMethod();

        HttpHandler handler = switch (method) {
            case "GET" -> new ReadUser();
            case "PUT" -> {
                if (!("moderator".equals(session.getRole()) || "admin".equals(session.getRole()))) {
                    ResponseUtil.sendResponse(exchange, 403, "Forbidden: Moderator or higher access required");
                    yield null;
                }
                yield new UpdateUserPassword();
            }
            case "POST", "DELETE" -> {
                if (!"admin".equals(session.getRole())) {
                    ResponseUtil.sendResponse(exchange, 403, "Forbidden: Admin access required");
                    yield null;
                }
                yield switch (method) {
                    case "POST" -> new CreateUser();
                    case "DELETE" -> new DeleteUser();
                    default -> new UnsupportedMethod(); // ¯\_(ツ)_/¯
                };
            }
            default -> new UnsupportedMethod();
        };

        if (handler != null) {
            handler.handle(exchange);
        }
    }
}

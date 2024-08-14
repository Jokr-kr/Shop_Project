package com.github.jokrkr.shopproject.server.controllers;

import com.github.jokrkr.shopproject.server.CRUD.UnsupportedMethod;
import com.github.jokrkr.shopproject.server.CRUD.Users.CreateUser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserController implements HttpHandler {

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        HttpHandler handler = switch (method) {
            case "GET" -> new ReadUser() ;
            case "POST" -> new CreateUser() ;
            case "PUT" -> new UpdateUserPassword();
            case "DELETE" -> new DeleteUser();
            default -> new UnsupportedMethod();
        };

        handler.handle(exchange);
    }
}


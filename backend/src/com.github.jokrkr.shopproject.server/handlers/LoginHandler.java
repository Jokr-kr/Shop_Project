package com.github.jokrkr.shopproject.server.handlers;

import com.github.jokrkr.shopproject.server.auth.LogOut;
import com.github.jokrkr.shopproject.server.CRUD.UnsupportedMethod;
import com.github.jokrkr.shopproject.server.auth.LogIn;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        HttpHandler handler = switch (method) {
            case "GET" -> new LogIn() ;
            case "DELETE" -> new LogOut();
            default -> new UnsupportedMethod(); // ¯\_(ツ)_/¯
        };
        handler.handle(exchange);
    }
}
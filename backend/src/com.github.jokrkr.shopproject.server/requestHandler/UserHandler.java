package com.github.jokrkr.shopproject.server.requestHandler;

import com.github.jokrkr.shopproject.server.CRUD.UnsupportedMethod;
import com.github.jokrkr.shopproject.server.CRUD.Users.CreateUser;
import com.github.jokrkr.shopproject.server.CRUD.Users.DeleteUser;
import com.github.jokrkr.shopproject.server.CRUD.Users.ReadUser;
import com.github.jokrkr.shopproject.server.CRUD.Users.UpdateUserPassword;
import com.github.jokrkr.shopproject.server.models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UserHandler implements HttpHandler {

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


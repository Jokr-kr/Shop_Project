package com.github.jokrkr.shopproject.server.handlers;

import com.github.jokrkr.shopproject.server.CRUD.Item.CreateItem;
import com.github.jokrkr.shopproject.server.CRUD.Item.ReadItem;
import com.github.jokrkr.shopproject.server.CRUD.Item.UpdateItem;
import com.github.jokrkr.shopproject.server.CRUD.Item.DeleteItem;
import com.github.jokrkr.shopproject.server.CRUD.UnsupportedMethod;
import com.github.jokrkr.shopproject.server.auth.AuthenticationUtil;
import com.github.jokrkr.shopproject.server.models.Session;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ItemHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Session session = AuthenticationUtil.authenticate(exchange);
        if (session == null) {
            return;
        }

        String method = exchange.getRequestMethod();

        HttpHandler handler = switch (method) {
            case "GET" -> new ReadItem();
            case "DELETE" -> new DeleteItem();
            case "POST", "PUT" -> {
                if (!"admin".equals(session.getRole()))
                {
                    ResponseUtil.sendResponse(exchange, 403, "Forbidden: Admin access required");
                    yield null;}

                yield switch (method) {
                    case "POST" -> new CreateItem();
                    case "PUT" -> new UpdateItem();
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

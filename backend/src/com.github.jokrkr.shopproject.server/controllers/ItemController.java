package com.github.jokrkr.shopproject.server.controllers;

import com.github.jokrkr.shopproject.server.handlers.CRUD.CreateItem;
import com.github.jokrkr.shopproject.server.handlers.CRUD.ReadItem;
import com.github.jokrkr.shopproject.server.handlers.CRUD.UpdateItem;
import com.github.jokrkr.shopproject.server.handlers.CRUD.DeleteItem;
import com.github.jokrkr.shopproject.server.handlers.UnsupportedMethod;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ItemController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        HttpHandler handler;

        switch (method) {
            case "GET":
                handler = new ReadItem();
                break;
            case "POST":
                handler = new CreateItem();
                break;
            case "PUT":
                handler = new UpdateItem();
                break;
            case "DELETE":
                handler = new DeleteItem();
                break;
            default:
                handler = new UnsupportedMethod();
                break;
        }

        handler.handle(exchange);
    }
}
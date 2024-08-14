package com.github.jokrkr.shopproject.server.controllers;

import com.github.jokrkr.shopproject.server.CRUD.Item.CreateItem;
import com.github.jokrkr.shopproject.server.CRUD.Item.ReadItem;
import com.github.jokrkr.shopproject.server.CRUD.Item.UpdateItem;
import com.github.jokrkr.shopproject.server.CRUD.Item.DeleteItem;
import com.github.jokrkr.shopproject.server.CRUD.UnsupportedMethod;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ItemController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        //------------------
        //this selects which handler to use
        HttpHandler handler = switch (method) {
            case "GET" -> new ReadItem();
            case "POST" -> new CreateItem();
            case "PUT" -> new UpdateItem();
            case "DELETE" -> new DeleteItem();
            default -> new UnsupportedMethod();
        };
        //------------------
        //gives the exchange to the selected handler
        handler.handle(exchange);
    }
}
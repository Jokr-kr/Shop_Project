package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.services.ItemService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class UpdateItem implements HttpHandler {
    private final ItemService itemService;

    public UpdateItem() {
        try {
            itemService = new ItemService();
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing ItemService", e);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Gson gson = new Gson();
            Item item = gson.fromJson(
                    new InputStreamReader(
                            exchange.getRequestBody(),
                            StandardCharsets.UTF_8),
                    Item.class);

            itemService.addItem(
                    item.getType(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());

            sendResponse(exchange, 200, "Item updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    //------------------------
    //
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
package com.github.jokrkr.shopproject.server.handlers.CRUD;

import com.github.jokrkr.shopproject.server.services.ItemService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadItem implements HttpHandler {
    private final ItemService itemService;

    public ReadItem() {
        try {
            itemService = new ItemService();
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing ItemService", e);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Retrieve items from the database
            ResultSet resultSet = itemService.getItems();
            List<String> items = new ArrayList<>();
            while (resultSet.next()) {
                String item = String.format("ID: %d, Type: %s, Name: %s, Price: %.2f, Quantity: %d",
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"));
                items.add(item);
            }

            // Prepare the response
            String response = String.join("\n", items);
            sendResponse(exchange, 200, response);

        } catch (SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
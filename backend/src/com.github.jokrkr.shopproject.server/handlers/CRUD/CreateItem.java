package com.github.jokrkr.shopproject.server.handlers.CRUD;

import com.github.jokrkr.shopproject.server.services.ItemService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class CreateItem implements HttpHandler {
    private final ItemService itemService;

    public CreateItem() {
        try {
            itemService = new ItemService();
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing ItemService", e);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Read the request body
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + body);

            // Parse the JSON body
            JSONObject jsonObject = new JSONObject(body);
            String type = jsonObject.getString("type");
            String name = jsonObject.getString("name");
            double price = jsonObject.getDouble("price");
            int quantity = jsonObject.getInt("quantity");

            // Validate input
            if (type == null || name == null || price <= 0 || quantity <= 0) {
                sendResponse(exchange, 400, "Invalid input data");
                return;
            }

            // Add the item using ItemService
            itemService.addItem(type, name, price, quantity);

            // Send a success response
            sendResponse(exchange, 200, "Item created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.services.itemService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class DeleteItem implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeleteItem.class);
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        itemService itemService = null;
        try {
            itemService = new itemService();
            Item item = parseRequestBody(exchange);
            if (!validateItem(item)) {
                logger.warn("Validation failed for item: {}", item);
                sendResponse(exchange, 400, "Invalid input data");
                return;
            }

            itemService.removeItem(
                    item.getType(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());

            sendResponse(exchange, 200, "Item deleted successfully");
            logger.info("Item deleted successfully: {}", item.getName());

        } catch (SQLException e) {
            logger.error("Database error", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (itemService != null) {
                itemService.close();
            }
        }
    }

    //------------------------
    //
    private Item parseRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder rawJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                rawJson.append(line);
            }
            String jsonString = rawJson.toString();
            logger.info("Raw JSON Input: {}", jsonString);
            return gson.fromJson(jsonString, Item.class);
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

    //------------------------
    //
    private boolean validateItem(Item item) {
        return item.getType() != null && !item.getType().isEmpty() &&
                item.getName() != null && !item.getName().isEmpty() &&
                item.getPrice() > 0 && item.getQuantity() > 0;
    }
}


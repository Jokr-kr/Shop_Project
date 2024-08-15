package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.services.itemService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class CreateItem implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(CreateItem.class);
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        itemService itemService = null;
        try {
            itemService = new itemService();
            Item item = parseRequestBody(exchange);
            validateItem(item);

            itemService.addItem(item.getType(), item.getName(), item.getPrice(), item.getQuantity());
            sendResponse(exchange, 200, "Item created successfully");
            logger.info("Item created successfully: {}", item.getName());

        } catch (JsonSyntaxException e) {
            logger.error("Invalid JSON format", e);
            sendResponse(exchange, 400, "Invalid JSON format");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input data", e);
            sendResponse(exchange, 400, "Invalid input data");
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
    private void validateItem(Item item) {
        if (item.getType() == null || item.getName() == null || item.getPrice() <= 0 || item.getQuantity() <= 0 || item.getValue() <= 0) {
            logger.warn("Validation failed for item: {}", item);
            throw new IllegalArgumentException("Invalid input data");
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

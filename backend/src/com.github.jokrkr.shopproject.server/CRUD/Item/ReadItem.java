package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.services.itemService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadItem implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(ReadItem.class);
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        itemService itemService = null;
        try {
            itemService = new itemService();
            List<Item> items = getItemsFromDatabase(itemService);
            String jsonResponse = gson.toJson(items);

            sendResponse(exchange, 200, jsonResponse);

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

    private List<Item> getItemsFromDatabase(itemService itemService) throws SQLException {
        ResultSet resultSet = itemService.getItems();
        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            Item item = new Item(
                    resultSet.getString("type"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("value"));
            items.add(item);
        }
        return items;
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

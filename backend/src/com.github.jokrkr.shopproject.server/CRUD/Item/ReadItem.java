package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.ItemService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadItem implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(ReadItem.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ItemService itemService = null;
        try {
            itemService = new ItemService();
            List<Item> items = getItemsFromDatabase(itemService);
            JSONArray jsonResponse = new JSONArray();

            for (Item item : items) {
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("type", item.getType());
                jsonItem.put("name", item.getName());
                jsonItem.put("price", item.getPrice());
                jsonItem.put("quantity", item.getQuantity());
                jsonItem.put("value", item.getValue());
                jsonResponse.put(jsonItem);
            }

            ResponseUtil.sendResponse(exchange, 200, jsonResponse.toString());

        } catch (SQLException e) {
            logger.error("Database error", e);
            ResponseUtil.sendResponse(exchange, 500, new JSONObject().put("error", "Internal Server Error").toString());
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            ResponseUtil.sendResponse(exchange, 500, new JSONObject().put("error", "Internal Server Error").toString());
        } finally {
            if (itemService != null) {
                itemService.close();
            }
        }
    }

    private List<Item> getItemsFromDatabase(ItemService itemService) throws SQLException {
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
}
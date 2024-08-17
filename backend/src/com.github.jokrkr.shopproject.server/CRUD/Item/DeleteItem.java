package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.Utility.ParsingUtility;
import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.ItemService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.sql.SQLException;

public class DeleteItem implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeleteItem.class);


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ItemService itemService = null;
        try {
            itemService = new ItemService();
            Item item = ParsingUtility.parseItem(exchange);
            if (!validateItem(item)) {
                logger.warn("Validation failed for item: {}", item);
                ResponseUtil.sendResponse(exchange, 400, "Invalid input data");
                return;
            }

            itemService.removeItem(
                    item.getType(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());

            ResponseUtil.sendResponse(exchange, 200, "Item deleted successfully");
            logger.info("Item deleted successfully: {}", item.getName());

        } catch (SQLException e) {
            logger.error("Database error", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            ResponseUtil.sendResponse(exchange, 500, "Internal Server Error");
        } finally {
            if (itemService != null) {
                itemService.close();
            }
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

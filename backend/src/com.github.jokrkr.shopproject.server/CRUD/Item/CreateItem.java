package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.services.ItemService;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.Utility.ParsingUtility;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;



public class CreateItem implements HttpHandler {
    private static final Logger logger = LoggerFactory.getLogger(CreateItem.class);


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ItemService itemService = null;
        try {
            itemService = new ItemService();
            Item item = ParsingUtility.parseItem(exchange);

            validateItem(item);

            itemService.addItem(
                    item.getType(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());

            logger.info("Item created successfully: {}", item.getName());
            ResponseUtil.sendResponse(exchange, 200, "Item created successfully");

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input data", e);
            ResponseUtil.sendResponse(exchange, 400, "Invalid input data");
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
    private void validateItem(Item item) {
        if (item.getType() == null || item.getName() == null || item.getPrice() <= 0 || item.getQuantity() <= 0 || item.getValue() <= 0) {
            logger.warn("Validation failed for item: {}", item);
            throw new IllegalArgumentException("Invalid input data");
        }
    }
}

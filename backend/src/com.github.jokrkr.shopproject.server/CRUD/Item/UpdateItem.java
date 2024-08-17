package com.github.jokrkr.shopproject.server.CRUD.Item;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.github.jokrkr.shopproject.server.services.ItemService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static com.github.jokrkr.shopproject.server.Utility.ParsingUtility.parseItem;

public class UpdateItem implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            ItemService itemService = new ItemService();
            Item item = parseItem(exchange);
            itemService.addItem(
                    item.getType(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());

            ResponseUtil.sendResponse(exchange,200, "Item updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.sendResponse(exchange,500, "Internal Server Error");
        }
    }
}
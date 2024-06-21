package com.github.jokrkr.shopproject.server.handlers.CRUD;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class requestBodyHandling{

    String type;
    String name;
    double price;
    int quantity;

    public requestBodyHandling(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        JSONObject jsonBody = new JSONObject(body);
        this.type = jsonBody.getString("type");
        this.name = jsonBody.getString("name");
        this.price = jsonBody.getDouble("price");
        this.quantity = jsonBody.getInt("quantity");

        validateInput();
    }

    private void validateInput() {
        if (this.type == null || this.type.isEmpty() || this.name == null || this.name.isEmpty() || this.price <= 0 || this.quantity <= 0) {
            throw new IllegalArgumentException("Invalid input data");
        }
    }
}





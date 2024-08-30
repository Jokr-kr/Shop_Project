package com.github.jokrkr.shopproject.server.Utility;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.models.Role;
import com.github.jokrkr.shopproject.server.models.User;

import com.sun.net.httpserver.HttpExchange;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static com.github.jokrkr.shopproject.server.models.Role.regular;

public class ParsingUtility {

    private static final Logger logger = Logger.getLogger(ParsingUtility.class.getName());

    public static User parseUser(HttpExchange exchange) throws IOException {
        JSONObject object = parser(exchange);

        String userName = "Default";
        String password = "Default";
        Role role = regular;

        // Log JSON for debugging
        logger.info("Received JSON: " + object);

        if (object.has("username")) {
            userName = object.optString("username", "Default"); // optString to avoid exceptions
        }
        if (object.has("password")) {
            password = object.optString("password", "Default");
        }
        if (object.has("role")) {
            String roleString = object.optString("role", "regular");
            role = Role.valueOf(roleString);
        }

        User user = new User(userName, password, role);
        logger.info("Created user with fields: " + user);

        return user;
    }

    public static Item parseItem(HttpExchange exchange) throws IOException {
        JSONObject object = parser(exchange);

        String type = "Default";
        String name = "Default";
        double price = 0;
        int quantity = 0;
        double value = 0;

        // Log JSON for debugging
        logger.info("Received JSON: " + object);

        if (object.has("type")) {
            type = object.optString("type", "Default");
        }
        if (object.has("name")) {
            name = object.optString("name", "Default");
        }
        if (object.has("price")) {
            price = object.optDouble("price", 0); // optDouble to avoid exceptions
        }
        if (object.has("quantity")) {
            quantity = object.optInt("quantity", 0); // optInt to avoid exceptions
        }
        if (object.has("value")) {
            value = object.optDouble("value", 0); // optDouble to avoid exceptions
        }

        Item item = new Item(type, name, price, quantity, value);
        logger.info("Created Item with fields: " + item);

        return item;
    }

    public static JSONObject parser(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder rawJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                rawJson.append(line);
            }
            String jsonString = rawJson.toString();

            logger.info("Raw JSON received: " + jsonString);  // Log JSON for debugging

            return new JSONObject(jsonString); // Convert raw JSON string to JSONObject
        }
    }
}

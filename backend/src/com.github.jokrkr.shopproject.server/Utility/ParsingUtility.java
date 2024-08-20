package com.github.jokrkr.shopproject.server.Utility;

import com.github.jokrkr.shopproject.server.models.Item;
import com.github.jokrkr.shopproject.server.models.Role;
import com.github.jokrkr.shopproject.server.models.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static com.github.jokrkr.shopproject.server.models.Role.regular;

public class ParsingUtility {

    private static final Logger logger = Logger.getLogger(ParsingUtility.class.getName());

    public static User parseUser(HttpExchange exchange) throws IOException {

        JsonObject object = parser(exchange);

        String userName = "Default";
        String password = "Default";
        Role role = regular;

        if (object.has("userName")) {
            userName = object.get("userName").getAsString();
        }
        if (object.has("password")) {
            password = object.get("password").getAsString();
        }
        if (object.has("role")) {
            String roleString = object.get("role").getAsString();
            role = Role.valueOf(roleString);
        }

        User user = new User(userName, password, role);
        logger.info("Created user with fields: " + user);

        return user;
    }


    public static Item parseItem(HttpExchange exchange) throws IOException {

        JsonObject object = parser(exchange);

        String type = "Default";
        String name = "Default";
        double price = 0;
        int quantity = 0;
        double value = 0;

        if (object.has("type")) {
            type = object.get("type").getAsString();
        }
        if (object.has("name")) {
            name = object.get("name").getAsString();
        }
        if (object.has("price")) {
            price = object.get("price").getAsDouble();
        }
        if (object.has("quantity")) {
            quantity = object.get("quantity").getAsInt();
        }
        if (object.has("value")) {
            value = object.get("value").getAsDouble();
        }
        Item item = new Item(
                type,
                name,
                price,
                quantity,
                value);
        ;
        logger.info("Created Item with fields: " + item);

        return item;

    }

    public static JsonObject parser(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            StringBuilder rawJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                rawJson.append(line);
            }
            String jsonString = rawJson.toString();

            return JsonParser.parseString(jsonString).getAsJsonObject();
        }
    }
}

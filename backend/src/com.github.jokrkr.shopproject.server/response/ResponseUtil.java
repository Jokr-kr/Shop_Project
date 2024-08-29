package com.github.jokrkr.shopproject.server.response;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    public static void sendResponse(HttpExchange exchange, int statusCode, JSONObject response) throws IOException {
        String responseString = response.toString();
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseString.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }
}
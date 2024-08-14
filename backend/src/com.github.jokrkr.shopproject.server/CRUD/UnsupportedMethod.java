package com.github.jokrkr.shopproject.server.CRUD;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class UnsupportedMethod implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Method not supported";
        exchange.sendResponseHeaders(405, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

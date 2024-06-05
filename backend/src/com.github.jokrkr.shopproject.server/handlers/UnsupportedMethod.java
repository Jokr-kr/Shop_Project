package com.github.jokrkr.shopproject.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class UnsupportedMethod implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}

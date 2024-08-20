package com.github.jokrkr.shopproject.server.CRUD;

import com.github.jokrkr.shopproject.server.response.ResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class UnsupportedMethod implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
            ResponseUtil.sendResponse(exchange,405,"Method not allowed");
        }
    }


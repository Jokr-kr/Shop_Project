package com.github.jokrkr.shopproject.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.github.jokrkr.shopproject.server.controllers.ItemController;
import com.sun.net.httpserver.HttpServer;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/items", new ItemController());
        server.setExecutor(null);
        server.start();
    }
}

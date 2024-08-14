package com.github.jokrkr.shopproject.server;

import com.github.jokrkr.shopproject.server.controllers.ItemController;
import com.github.jokrkr.shopproject.server.controllers.UserController;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class ServerApp {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) throws IOException, SQLException {
        // creating the address for the server
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
        // creating server
        HttpServer server = HttpServer.create(address, 0); // reminder backlog is related to amount of incoming requests
        server.createContext("/items", new ItemController()); // context/endpoint
        server.createContext("/users", new UserController());
        server.setExecutor(null);   // basic request handler for the server
        server.start();             // starts the server
        logger.info("Server is listening on port 8080");
    }
}

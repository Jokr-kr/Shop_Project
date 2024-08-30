package com.github.jokrkr.shopproject.server;

import com.github.jokrkr.shopproject.server.handlers.ItemHandler;
import com.github.jokrkr.shopproject.server.handlers.UserHandler;
import com.github.jokrkr.shopproject.server.handlers.LoginHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

//todo -- consider setting up https

public class ServerApp {
    private static final Logger logger = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) throws IOException, SQLException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080); //todo this can be changed based on personal preferences

        HttpServer server = HttpServer.create(address, 0);

        server.createContext("/items", new ItemHandler());
        server.createContext("/users", new UserHandler());
        server.createContext("/login", new LoginHandler());

        server.setExecutor(null);
        server.start();
        logger.info("Server is listening on port 8080");
    }
}

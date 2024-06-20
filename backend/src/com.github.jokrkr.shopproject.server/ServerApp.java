package com.github.jokrkr.shopproject.server;

import com.github.jokrkr.shopproject.server.controllers.ItemController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class ServerApp {

    public static void main(String[] args) throws IOException, SQLException {
        //creating the address for the server
        InetSocketAddress address = new InetSocketAddress("localhost", 8080);
        //creating server
        HttpServer server = HttpServer.create(address, 0); //reminder backlog is related to amount of incoming requests
        // 0 == default, which means it's decided by the system
        server.createContext("/items", new ItemController()); //context/endpoint

        server.setExecutor(null);   //basic request handler for the server
        server.start();             //starts the server
        System.out.println("server is listening on port 8080");
    }
}

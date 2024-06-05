//namespace
package com.github.jokrkr.shopproject.server;

//exception handling
import java.io.IOException;

//server imports
import com.sun.net.httpserver.HttpServer;   //creates HTTP server
import java.net.InetSocketAddress;          //specifying the address and port the server will listen on

// self-made imports
import com.github.jokrkr.shopproject.server.controllers.ItemController; //controller for handling /items requests


public class ServerApp {

    public static void main(String[] args) throws IOException {
        //creating the address for the server
        InetSocketAddress address = new InetSocketAddress("197.0.0.1", 8080);
        //creating server
        HttpServer server = HttpServer.create(address, 0); //reminder backlog is related to amount of incoming requests
                                                                   // 0 == default, which means it's decided by the system
        server.createContext("/items", new ItemController()); //context/endpoint

        server.setExecutor(null);   //basic request handler for the server
        server.start();             //starts the server
        System.out.println("server is listening on port 8080");
    }
}

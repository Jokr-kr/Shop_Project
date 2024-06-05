package com.github.jokrkr.shopproject.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // Retrieve database configuration from environment variables
    private static final String URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getProperty("DATABASE_USERNAME");
    private static final String PASSWORD = System.getProperty("DATABASE_PASSWORD");

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Database environment variables not set");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
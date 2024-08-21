package com.github.jokrkr.shopproject.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String BASE_URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("DATABASE_USERNAME");
    private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (BASE_URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Database environment variables not set");
        }

        return DriverManager.getConnection(BASE_URL, USER, PASSWORD);
    }
}
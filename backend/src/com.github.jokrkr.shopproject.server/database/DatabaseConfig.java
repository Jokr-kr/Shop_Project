package com.github.jokrkr.shopproject.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String BASE_URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("DATABASE_USERNAME");
    private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");


    public static Connection getConnection(String dbName) throws SQLException {
        System.out.println(BASE_URL + "/" + dbName + USER + " " + PASSWORD + " database connection");
        if (BASE_URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Database environment variables not set");

        }
        String fullUrl = BASE_URL + "/" + dbName;
        return DriverManager.getConnection(fullUrl, USER, PASSWORD);
    }
}
package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            // Connect to the 'inventory' database
            System.out.println("Connecting to the 'inventory' database...");
            Connection inventoryConn = DatabaseConfig.getConnection("inventory");
            runQuery(inventoryConn, "SELECT * FROM items");  // Assuming 'items' is a table in 'inventory'
            inventoryConn.close();

            // Connect to the 'users' database
            System.out.println("Connecting to the 'users' database...");
            Connection usersConn = DatabaseConfig.getConnection("users");
            runQuery(usersConn, "SELECT * FROM users");
            usersConn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to run a query and print results
    private static void runQuery(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Get metadata to dynamically print column names
        int columnCount = rs.getMetaData().getColumnCount();

        // Print out the column names
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(rs.getMetaData().getColumnName(i) + "\t");
        }
        System.out.println();

        // Print out the result rows
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }

        rs.close();
        stmt.close();
    }
}
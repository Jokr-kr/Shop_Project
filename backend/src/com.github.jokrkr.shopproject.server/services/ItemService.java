package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;

import java.sql.*;

public class ItemService
{
    private final Connection conn;

    public ItemService() throws SQLException
    {
       conn  = DatabaseConfig.getConnection();
    }


    // Select all data
    public ResultSet getItems() throws SQLException
    {
        String query = "SELECT * FROM items";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    public void printItems() throws SQLException {
        ResultSet rs = getItems();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
            }
            System.out.println();
        }
    }

    //inserting new item('s) or increasing quantity
    public void addItem(String type, String name, double price, int quantity) throws SQLException
    {
        String selectSQL = "SELECT id, quantity FROM items WHERE type = ? AND name = ? AND price = ?";
        String updateSQL = "UPDATE items SET quantity = quantity + ? WHERE id = ?";
        String insertSQL = "INSERT INTO items (type, name, price, quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            // Check if the item already exists with the same type, name, and price
            selectStmt.setString(1, type);
            selectStmt.setString(2, name);
            selectStmt.setDouble(3, price);
            ResultSet rs = selectStmt.executeQuery();   /*if the result from selectStmt returns a row in the ResultSet
                                                          it means there is an existing item that matches*/
            if (rs.next()) {
                // Item exists, update the quantity
                int id = rs.getInt("id");
                int existingQuantity = rs.getInt("quantity");

                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();

            } else {
                // Item does not exist, insert a new item
                insertStmt.setString(1, type);
                insertStmt.setString(2, name);
                insertStmt.setDouble(3, price);
                insertStmt.setInt(4, quantity);
                insertStmt.executeUpdate();

                System.out.println("Inserted new item: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void removeItem(String type, String name, double price, int quantityToDecrement) throws SQLException {
        String selectSQL = "SELECT id, quantity FROM items WHERE type = ? AND name = ? AND price = ?";
        String updateSQL = "UPDATE items SET quantity = quantity - ? WHERE id = ?";
        String deleteSQL = "DELETE FROM items WHERE id = ?";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {

            // Check if the item exists with the specified type, name, and price
            selectStmt.setString(1, type);
            selectStmt.setString(2, name);
            selectStmt.setDouble(3, price);
            ResultSet rs = selectStmt.executeQuery();       /*if the result from selectStmt returns a row in the ResultSet
                                                              it means there is an existing item that matches*/
            if (rs.next()) {
                // Item exists, get the current quantity
                int id = rs.getInt("id");
                int existingQuantity = rs.getInt("quantity");

                if (existingQuantity > quantityToDecrement) {
                    // Decrement the quantity
                    updateStmt.setInt(1, quantityToDecrement);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    System.out.println("Decremented item: " + name + ". New quantity: " + (existingQuantity - quantityToDecrement));
                } else {
                    // Delete the item
                    deleteStmt.setInt(1, id);
                    deleteStmt.executeUpdate();

                    System.out.println("Deleted item: " + name);
                }
            } else {
                System.out.println("Item not found: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
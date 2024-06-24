package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;

import java.sql.*;

public class ItemService {
    private final Connection conn;
    private static final String SELECT_SQL = "SELECT id, quantity FROM items WHERE type = ? AND name = ? AND price = ?";
    private static final String UPDATE_SQL = "UPDATE items SET quantity = quantity + ? WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO items (type, name, price, quantity) VALUES (?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM items WHERE id = ?";

    //------------------------
    // establishes connection to the database
    public ItemService() throws SQLException {
        conn = DatabaseConfig.getConnection();
    }

    //------------------------
    // Select all data
    public ResultSet getItems() throws SQLException {
        String getAll = "SELECT type, name, price, quantity, value FROM items";
        PreparedStatement stmt = conn.prepareStatement(getAll);
        return stmt.executeQuery();
    }

    //------------------------
    // Inserting new item('s) or increasing quantity
    public void addItem(String type, String name, double price, int quantity) throws SQLException {
        try (PreparedStatement selectStmt = conn.prepareStatement(SELECT_SQL);
             PreparedStatement updateStmt = conn.prepareStatement(UPDATE_SQL);
             PreparedStatement insertStmt = conn.prepareStatement(INSERT_SQL)) {

            if (itemExists(selectStmt, type, name, price)) {
                int id = getItemId(selectStmt, type, name, price);
                updateItemQuantity(updateStmt, id, quantity);
            } else {
                insertNewItem(insertStmt, type, name, price, quantity);
                System.out.println("Inserted new item: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //------------------------
    // Removing item('s) or decreasing quantity
    public void removeItem(String type, String name, double price, int quantityToDecrement) throws SQLException {
        try (PreparedStatement selectStmt = conn.prepareStatement(SELECT_SQL);
             PreparedStatement updateStmt = conn.prepareStatement(UPDATE_SQL);
             PreparedStatement deleteStmt = conn.prepareStatement(DELETE_SQL)) {

            if (itemExists(selectStmt, type, name, price)) {
                int id = getItemId(selectStmt, type, name, price);
                int existingQuantity = getItemQuantity(selectStmt, type, name, price);

                if (existingQuantity > quantityToDecrement) {
                    updateItemQuantity(updateStmt, id, -quantityToDecrement);
                    System.out.println("removed item's: " + name + ". New quantity: " + (existingQuantity - quantityToDecrement));
                } else if (existingQuantity == quantityToDecrement) {
                    deleteItem(deleteStmt, id);
                    System.out.println("Deleted item: " + name);
                } else {
                    throw new IllegalArgumentException("Quantity to remove is greater than the existing quantity");
                }
            } else {
                System.out.println("Item not found: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //------------------------
    // checks if item exists in database
    private boolean itemExists(PreparedStatement selectStmt, String type, String name, double price) throws SQLException {
        selectStmt.setString(1, type);
        selectStmt.setString(2, name);
        selectStmt.setDouble(3, price);
        try (ResultSet rs = selectStmt.executeQuery()) {
            return rs.next();
        }
    }

    //------------------------
    //gets the item id from the database
    private int getItemId(PreparedStatement selectStmt, String type, String name, double price) throws SQLException {
        selectStmt.setString(1, type);
        selectStmt.setString(2, name);
        selectStmt.setDouble(3, price);
        try (ResultSet rs = selectStmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Item not found");
        }
    }

    //------------------------
    //checks an item quantity
    private int getItemQuantity(PreparedStatement selectStmt, String type, String name, double price) throws SQLException {
        selectStmt.setString(1, type);
        selectStmt.setString(2, name);
        selectStmt.setDouble(3, price);
        try (ResultSet rs = selectStmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("quantity");
            }
            throw new SQLException("Item not found");
        }
    }

    //------------------------
    //updates an item quantity
    private void updateItemQuantity(PreparedStatement updateStmt, int id, int quantity) throws SQLException {
        updateStmt.setInt(1, quantity);
        updateStmt.setInt(2, id);
        updateStmt.executeUpdate();
    }

    //------------------------
    //adds a new item
    private void insertNewItem(PreparedStatement insertStmt, String type, String name, double price, int quantity) throws SQLException {
        insertStmt.setString(1, type);
        insertStmt.setString(2, name);
        insertStmt.setDouble(3, price);
        insertStmt.setInt(4, quantity);
        insertStmt.executeUpdate();
    }

    //------------------------
    //deletes an item
    private void deleteItem(PreparedStatement deleteStmt, int id) throws SQLException {
        deleteStmt.setInt(1, id);
        deleteStmt.executeUpdate();
    }
}

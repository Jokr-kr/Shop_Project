package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final Connection conn;
    private static final String SELECT_SQL = "SELECT id, quantity FROM items WHERE type = ? AND name = ? AND price = ?";
    private static final String UPDATE_SQL = "UPDATE items SET quantity = quantity + ? WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO items (type, name, price, quantity) VALUES (?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM items WHERE id = ?";

    //------------------------
    // establishes connection to the database
    public ItemService() throws SQLException {
        this.conn = DatabaseConfig.getConnection();
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
                logger.info("Inserted new item: {}", name);
            }
        } catch (SQLException e) {
            logger.error("Failed to add item", e);
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
                    logger.info("Removed items: {}. New quantity: {}", name, existingQuantity - quantityToDecrement);
                } else if (existingQuantity == quantityToDecrement) {
                    deleteItem(deleteStmt, id);
                    logger.info("Deleted item: {}", name);
                } else {
                    throw new IllegalArgumentException("Quantity to remove is greater than the existing quantity");
                }
            } else {
                logger.info("Item not found: {}", name);
            }
        } catch (SQLException e) {
            logger.error("Failed to remove item", e);
            throw e;
        }
    }

    //------------------------
    //setting up the item
    private ResultSet getItemResultSet(PreparedStatement stmt, String type, String name, double price) throws SQLException {
        stmt.setString(1, type);
        stmt.setString(2, name);
        stmt.setDouble(3, price);
        return stmt.executeQuery();
    }

    //------------------------
    // checks if item exists in database
    private boolean itemExists(PreparedStatement selectStmt, String type, String name, double price) throws SQLException {
        try (ResultSet rs = getItemResultSet(selectStmt, type, name, price)) {
            return rs.next();
        }
    }

    //------------------------
    //gets the item id from the database
    private int getItemId(PreparedStatement selectStmt, String type, String name, double price) throws SQLException {
        try (ResultSet rs = getItemResultSet(selectStmt, type, name, price)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Item not found");
        }
    }

    //------------------------
    //checks an item quantity
    private int getItemQuantity(PreparedStatement selectStmt, String type, String name, double price) throws SQLException {
        try (ResultSet rs = getItemResultSet(selectStmt, type, name, price)) {
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

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

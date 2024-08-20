package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;
import com.github.jokrkr.shopproject.server.models.Role;
import com.github.jokrkr.shopproject.server.models.User;
import com.github.jokrkr.shopproject.server.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class userService {
    private static final Logger logger = LoggerFactory.getLogger(userService.class);
    private final Connection conn;

    //------------------------
    // establishes connection to the database
    public userService() throws SQLException {
        this.conn = DatabaseConfig.getConnection("users");
    }

    //------------------------
    // user creation
    public void createUser(User newUser) throws SQLException {
        String query = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newUser.getUserName());
            ps.setString(2, newUser.getPassword());
            ps.setString(3, newUser.getRole().toString());
            ps.executeUpdate();
        }
    }

    //------------------------
    // user deletion
    public boolean deleteUser(String username) throws SQLException {
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Returns true if a user was deleted, false otherwise
        }
    }
    //------------------------
    // retrieval of user data
    public ResultSet getUsers() throws SQLException {
        String Query = "SELECT id, username, role, created_at FROM users";
        PreparedStatement ps = conn.prepareStatement(Query);
        return ps.executeQuery();
    }

    //------------------------
    // retrieval of password hash
    public ResultSet getpasswordhash(String username) throws SQLException {
        String Query = "SELECT password_hash FROM users WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, username);
        return ps.executeQuery();
    }

    //------------------------
    // for changing password
    public void updatepassword(User User) throws SQLException {

        String Query = "update users set password_hash = ? where username = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, User.getPassword());
        ps.setString(2, User.getUserName());
        ps.executeUpdate();
    }

    //------------------------
    // closes connection
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




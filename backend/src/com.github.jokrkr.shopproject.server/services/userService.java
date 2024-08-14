package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;
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

    public ResultSet getUsers() throws SQLException {
        String user = "SELECT * FROM users";
        PreparedStatement ps = conn.prepareStatement(user);
        return ps.executeQuery();
    }
}




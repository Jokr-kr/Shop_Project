package com.github.jokrkr.shopproject.server.services;

import com.github.jokrkr.shopproject.server.database.DatabaseConfig;
import com.github.jokrkr.shopproject.server.handlers.SessionHandler;
import com.github.jokrkr.shopproject.server.models.Role;
import com.github.jokrkr.shopproject.server.response.LoginResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    private final Connection conn;

    public LoginService() throws SQLException {
        this.conn = DatabaseConfig.getConnection();
    }

    public LoginResponse authenticate(String username, String password) throws SQLException {
        String query = "SELECT password_hash FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                if (storedHash.equals(password)) {
                    String sessionId = SessionHandler.createSession(username, "userRole");
                    return LoginResponse.success(sessionId);
                } else {
                    return LoginResponse.incorrectPassword();
                }
            } else {
                return LoginResponse.usernameNotFound();
            }
        } catch (SQLException e) {
            return LoginResponse.serverError();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public Role getRole(String username) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String roleString = rs.getString("role");
                return Role.valueOf(roleString.toUpperCase());
            } else {
                throw new SQLException("User not found");
            }
        }
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

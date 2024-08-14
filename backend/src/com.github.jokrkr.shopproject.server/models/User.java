package com.github.jokrkr.shopproject.server.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class User {
    private final String _userName;
    private final String _passwordHash;
    private final Role _role;

    public User(String UserName, String Password, Role Role) {
        this._userName = UserName;
        this._passwordHash = HashPassword(Password);
        this._role = Role;
    }

    private String HashPassword(String password) {
        try {
 MessageDigest md =MessageDigest.getInstance("SHA-256");
 byte[] hash = md.digest(password.getBytes());
 StringBuilder hexString = new StringBuilder();
 for (byte b : hash) {
     hexString.append(String.format("%02X", b));
 }
 return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("error while hashing password", e);
        }
    }

    public String getUserName() {return _userName;}
    public String getPassword() {return _passwordHash;}
    public Role getRole() {return _role;}

}

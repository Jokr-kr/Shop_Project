package com.github.jokrkr.shopproject.server.models;

public class User {
    private final String _userName;
    private final String _password;
    private boolean _admin;
    private final String _role;

    public User(String UserName, String Password, boolean Admin, String Role) {
        this._userName = UserName;
        this._password = Password;
        this._role = Role;
        if (Role.equals("Admin")) {_admin = true;}
    }
    public String getUserName() {return _userName;}
    public String getPassword() {return _password;}
    public boolean isAdmin() {return _admin;}
    public String getRole() {return _role;}

}

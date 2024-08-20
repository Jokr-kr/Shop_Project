package com.github.jokrkr.shopproject.server.models;

public enum Role {
    regular(0),
    moderator(1),
    admin(2);

    private final int _level;

    Role(int level) {
        this._level = level;
    }

    public int getLevel() {return _level;}
}
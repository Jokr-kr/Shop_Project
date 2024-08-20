package com.github.jokrkr.shopproject.state;

public class Session {
    private static Session instance;
    private String sessionId;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void clearSession() {
        this.sessionId = null;
    }
}
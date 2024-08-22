package com.github.jokrkr.shopproject.state;

public class Session {
    private static Session instance;
    private static String sessionId;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        Session.sessionId = sessionId;
    }

    public static void clear() {
        sessionId = null;
    }
}
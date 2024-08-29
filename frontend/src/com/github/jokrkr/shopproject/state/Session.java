package com.github.jokrkr.shopproject.state;

public class Session {
    private static Session instance;
    private static String sessionId;
    private static String userRole;

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

    public static String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        Session.userRole = userRole;
    }

    public static void clear() {
        sessionId = null;
        userRole = null;
    }
}
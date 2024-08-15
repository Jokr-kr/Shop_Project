package com.github.jokrkr.shopproject.server.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    // A thread-safe map to store sessions
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    // Method to create a session and return the session ID
    public static String createSession(String username, String role) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(username, role));
        return sessionId;
    }

    // Method to retrieve session ID
    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    // Method to remove a session (used during logout)
    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }


    public static class Session {
        private final String username;
        private final String role;

        public Session(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
}
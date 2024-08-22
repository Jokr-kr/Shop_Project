package com.github.jokrkr.shopproject.server.handlers;

import com.github.jokrkr.shopproject.server.models.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionHandler {
    private static final Map<String, Session> sessions = new HashMap<>();

    public static String createSession(String username, String role) {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(username, role);
        sessions.put(sessionId, session);
        return sessionId;
    }

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}

package com.skpijtk.springboot_boilerplate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionManager {
    private final ConcurrentHashMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(Long userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void removeSession(Long userId) {
        sessions.remove(userId);
    }

    public WebSocketSession getSession(Long userId) {
        return sessions.get(userId);
    }

    public ConcurrentHashMap<Long, WebSocketSession> getAllSessions() {
        return sessions;
    }
}

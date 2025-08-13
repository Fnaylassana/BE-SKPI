package com.skpijtk.springboot_boilerplate.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        session.sendMessage(new TextMessage("✅ Connected to chat server"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession sender, TextMessage message) throws Exception {
        // Kirim balik ke pengirim (echo)
        sender.sendMessage(new TextMessage("🟡 Echo: " + message.getPayload()));

        // Atau broadcast ke semua yang terkoneksi
        for (WebSocketSession session : sessions) {
            if (session.isOpen() && session != sender) {
                session.sendMessage(new TextMessage("📢 User: " + message.getPayload()));
            }
        }
    }
}

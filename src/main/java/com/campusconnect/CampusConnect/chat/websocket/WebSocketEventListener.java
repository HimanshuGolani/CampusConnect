package com.campusconnect.CampusConnect.chat.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    /**
     * Handles WebSocket connection establishment.
     *
     * @param event the event triggered when a WebSocket connection is established
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("New WebSocket connection established. Session ID: {}", headerAccessor.getSessionId());
    }

    /**
     * Handles WebSocket disconnection.
     *
     * @param event the event triggered when a WebSocket connection is closed
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        log.info("WebSocket connection closed. Session ID: {}", sessionId);

        // You can perform additional actions here:
        // For example, notify others about a user going offline.
    }
}

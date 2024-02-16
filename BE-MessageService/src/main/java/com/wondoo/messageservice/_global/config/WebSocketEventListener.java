package com.wondoo.messageservice._global.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class WebSocketEventListener {
    @EventListener
    public void handleWebSocketConnectionEvent(SessionConnectEvent sessionConnectEvent){
        String sessionId = sessionConnectEvent.getMessage().getHeaders().get("simpSessionId").toString();
        log.info("[CONNECTION] SessionID: {}", sessionId);
    }

    @EventListener
    public void handlerWebSocketDisconnectionEvent(SessionDisconnectEvent sessionDisconnectEvent){
        String sessionId = sessionDisconnectEvent.getSessionId();
        log.info("[DISCONNECT] SessionID: {}", sessionId);
    }

    @EventListener
    private void handleSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("[SUBSCRIBE] Address: {}", accessor.getDestination());
    }

    @EventListener
    private void handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("[UNSUBSCRIBE] Address: {}", accessor.getDestination());
    }
}

package com.kocak.kmenuserver.service;

import com.kocak.kmenuserver.model.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// WebSocket mesajları için Spring Boot işlevleri
@Component
public class NotificationHandler extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Yeni sipariş geldiğinde, bildirimi WebSocket bağlantısı üzerinden gönder
        Order order = saveOrder(message.getPayload());
        messagingTemplate.convertAndSend("/topic/orders", order);
    }

    private Order saveOrder(String payload) {
        // Sipariş bilgilerini veritabanına kaydet
        // ...
        Order order = new Order();
        return order;
    }
}
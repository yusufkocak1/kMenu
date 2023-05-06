package com.kocak.kmenuserver.service;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

// WebSocket dinleyicisi Todo: daha sonra inşallah
@Component
public class WebSocketEventListener implements ApplicationListener<SessionSubscribeEvent> {
    private final OrderService orderService;

    public WebSocketEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if ("websocket/v1/restaurants/websocket/getOrder/".equals(headerAccessor.getDestination())) {
            String restaurantId = headerAccessor.getFirstNativeHeader("restaurantId");
            orderService.subscribe(restaurantId, headerAccessor.getSessionId());
        }
    }
}
package com.kocak.kmenuserver.service;

import com.kocak.kmenuserver.dto.OrderDTO;
import com.kocak.kmenuserver.model.Order;
import com.kocak.kmenuserver.model.OrderStatus;
import com.kocak.kmenuserver.repository.OrderRepository;
import com.kocak.kmenuserver.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class OrderService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, List<String>> restaurantSubscribers;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;

    public OrderService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.restaurantSubscribers = new ConcurrentHashMap<>();
    }

    public void placeOrder(OrderDTO orderDTO) {

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setMenuItem(productService.getMenuItemById(orderDTO.getMenuItemId()).orElseThrow());
        order.setTable(tableRepository.getById(orderDTO.getTableId()));
        order.setStatus(OrderStatus.PENDING);
        order.setQuantity(order.getQuantity());

        // Siparişi veritabanına kaydet
        orderRepository.save(order);

        // İlgili satıcılara bildirim gönder
        List<String> subscribers = restaurantSubscribers.getOrDefault(order.getTable().getRestaurantId(), Collections.emptyList());
        subscribers.forEach(subscriber -> messagingTemplate.convertAndSendToUser(subscriber, "/queue/order", order));
    }

    public void subscribe(String restaurantId, String sessionId) {
        restaurantSubscribers.computeIfAbsent(restaurantId, key -> new CopyOnWriteArrayList<>()).add(sessionId);
    }

    public void unsubscribe(String restaurantId, String sessionId) {
        restaurantSubscribers.computeIfPresent(restaurantId, (key, value) -> {
            value.remove(sessionId);
            return value.isEmpty() ? null : value;
        });
    }
}
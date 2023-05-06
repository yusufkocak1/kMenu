package com.kocak.kmenuserver.controller;

import com.kocak.kmenuserver.dto.OrderDTO;
import com.kocak.kmenuserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/restaurants")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Boolean> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.placeOrder(orderDTO);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(false);
        }

        return ResponseEntity.ok(true);
    }

}

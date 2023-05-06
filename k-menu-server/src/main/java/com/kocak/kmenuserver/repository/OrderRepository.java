package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}

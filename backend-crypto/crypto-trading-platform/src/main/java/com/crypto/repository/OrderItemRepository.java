package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}

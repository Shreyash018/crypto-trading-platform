package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

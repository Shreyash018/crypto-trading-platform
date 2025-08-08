package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long>  {

}

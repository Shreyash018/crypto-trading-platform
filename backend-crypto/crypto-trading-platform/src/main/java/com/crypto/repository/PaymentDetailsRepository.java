package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
	PaymentDetails getPaymentDetailsByUserId(Long userId);
}

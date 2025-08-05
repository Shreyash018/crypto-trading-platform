package com.crypto.service;

import com.crypto.model.Wallet;

public interface PaymentService {
	String createRazorpayOrder(Long userId, Long amount) throws Exception;

	Wallet processPaymentSuccess(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature,
			Long userId) throws Exception;

}

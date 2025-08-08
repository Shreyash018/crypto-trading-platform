package com.crypto.service;

import com.crypto.domain.PaymentMethod;
import com.crypto.dto.PaymentResponse;
import com.crypto.model.PaymentOrder;
import com.crypto.model.User;
import com.razorpay.RazorpayException;

public interface PaymentService {
	 PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

	    PaymentOrder getPaymentOrderById(Long id) throws Exception;

	    Boolean ProccedPaymentOrder (PaymentOrder paymentOrder,
	                                 String paymentId) throws RazorpayException;

	    PaymentResponse createRazorpayPaymentLink(User user,
	                                              Long Amount,
	                                              Long orderId) throws RazorpayException;

}

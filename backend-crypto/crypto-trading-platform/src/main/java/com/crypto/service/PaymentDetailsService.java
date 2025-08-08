package com.crypto.service;

import com.crypto.model.PaymentDetails;
import com.crypto.model.User;

public interface PaymentDetailsService {
	public PaymentDetails addPaymentDetails( String accountNumber,
            String accountHolderName,
            String ifsc,
            String bankName,
            User user
);

public PaymentDetails getUsersPaymentDetails(User user);

}

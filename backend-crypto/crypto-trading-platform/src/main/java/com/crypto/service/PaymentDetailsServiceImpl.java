package com.crypto.service;

import org.springframework.stereotype.Service;

import com.crypto.model.PaymentDetails;
import com.crypto.model.User;
import com.crypto.repository.PaymentDetailsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {


   
    private final PaymentDetailsRepository paymentDetailsRepository;

    
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user
    ) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailsRepository.getPaymentDetailsByUserId(user.getId());
    }
}

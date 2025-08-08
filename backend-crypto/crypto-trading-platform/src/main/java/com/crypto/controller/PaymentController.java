package com.crypto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.domain.PaymentMethod;
import com.crypto.dto.PaymentResponse;
import com.crypto.exception.UserException;
import com.crypto.model.PaymentOrder;
import com.crypto.model.User;
import com.crypto.service.PaymentService;
import com.crypto.service.UserService;
import com.razorpay.RazorpayException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PaymentController {

   
    private final UserService userService;

   
    private final PaymentService paymentService;



    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws UserException, RazorpayException{

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order= paymentService.createOrder(user, amount,paymentMethod);

       
         paymentResponse=paymentService.createRazorpayPaymentLink(user,amount,
                    order.getId());
        


        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }


}

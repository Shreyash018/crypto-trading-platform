package com.crypto.dto;

import com.crypto.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {
	 private String coinId;
	    private double quantity;
	    private OrderType orderType;
}

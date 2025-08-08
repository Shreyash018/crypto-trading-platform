package com.crypto.service;

import java.util.List;

import com.crypto.domain.OrderType;
import com.crypto.model.Coin;
import com.crypto.model.Order;
import com.crypto.model.OrderItem;
import com.crypto.model.User;


public interface OrderService {

	 Order createOrder(User user, OrderItem orderItem, OrderType orderType);

	    Order getOrderById(Long orderId);

	    List<Order> getAllOrdersForUser(Long userId, String orderType,String assetSymbol);

	    void cancelOrder(Long orderId);

//	    Order buyAsset(CreateOrderRequest req, Long userId, String jwt) throws Exception;

	    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;



}

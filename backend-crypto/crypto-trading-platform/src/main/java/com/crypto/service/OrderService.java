package com.crypto.service;

import java.util.List;

import com.crypto.domain.OrderType;
import com.crypto.model.Coin;
import com.crypto.model.Order;
import com.crypto.model.User;

public interface OrderService {

	Order createOrder(User user, Coin coin, int quantity ,OrderType orderType);
	
	Order getOrderById(Long orderId);
	
	List<Order> getAllOrdersForUser(Long userId);
	
	void cancelOrder(Long orderId);
	
	Order buyCoin(Coin coin, int quantity, User user) throws Exception;
	
	Order sellCoin(Coin coin, int quantity, User user) throws Exception;
	
}

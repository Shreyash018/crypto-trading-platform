package com.crypto.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.dto.OrderRequestDto;
import com.crypto.dto.OrderResponseDto;
import com.crypto.exception.CoinNotFoundException;
import com.crypto.exception.OrderException;
import com.crypto.exception.UserException;
import com.crypto.model.Coin;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.repository.CoinRepository;
import com.crypto.repository.UserRepository;
import com.crypto.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private final OrderService orderService;
	private final UserRepository userRepository;
	private final CoinRepository coinRepository;

	public OrderController(OrderService orderService, UserRepository userRepository, CoinRepository coinRepository) {
		this.orderService = orderService;
		this.userRepository = userRepository;
		this.coinRepository = coinRepository;
	}

	@PostMapping("/buy")
	public ResponseEntity<OrderResponseDto> buyOrder(@RequestBody OrderRequestDto request) throws Exception {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserException("User not found with ID " + request.getUserId()));
		Coin coin = coinRepository.findById(request.getCoinId())
				.orElseThrow(() -> new CoinNotFoundException("Coin not found with ID " + request.getCoinId()));
		if (request.getQuantity() <= 0) {
			throw new OrderException("Quantity must be greater than 0");
		}
		Order order = orderService.buyCoin(coin, request.getQuantity(), user);
		return new ResponseEntity<OrderResponseDto>(OrderResponseDto.from(order), HttpStatus.CREATED);
	}

	@PostMapping("/sell")
	public ResponseEntity<OrderResponseDto> sellOrder(@RequestBody OrderRequestDto request) throws Exception {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserException("User not found with ID " + request.getUserId()));
		Coin coin = coinRepository.findById(request.getCoinId())
				.orElseThrow(() -> new CoinNotFoundException("Coin not found with ID " + request.getCoinId()));
		if (request.getQuantity() <= 0) {
			throw new OrderException("Quantity must be greater than 0");
		}
		Order order = orderService.sellCoin(coin, request.getQuantity(), user);
		return new ResponseEntity<OrderResponseDto>(OrderResponseDto.from(order), HttpStatus.CREATED);
	}

	@GetMapping("{orderId}")
	public OrderResponseDto getOrderById(@PathVariable Long orderId) throws OrderException {
		Order order = orderService.getOrderById(orderId);
		if (order == null) {
			throw new OrderException("Order not found with ID " + orderId);
		}
		return OrderResponseDto.from(order);
	}

	@GetMapping("/user/{userId}")
	public List<OrderResponseDto> getAllOrdersForUser(@PathVariable Long userId) throws UserException {
		userRepository.findById(userId).orElseThrow(() -> new UserException("User not found with ID " + userId));
		return orderService.getAllOrdersForUser(userId).stream().map(OrderResponseDto::from)
				.collect(Collectors.toList());
	}
	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) throws OrderException{
		orderService.cancelOrder(orderId);
		return ResponseEntity.ok("Order cancelled successfully!");
	}
}

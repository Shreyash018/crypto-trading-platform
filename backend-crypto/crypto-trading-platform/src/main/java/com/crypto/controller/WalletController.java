package com.crypto.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.exception.WalletException;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.OrderRepository;
import com.crypto.repository.UserRepository;
import com.crypto.service.WalletService;
import com.crypto.service.WalletTransactionService;

@RestController
@RequestMapping("/wallet")
public class WalletController {
	@Autowired
	private WalletService walletService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletTransactionService walletTransactionService;
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping("/create/{userId}")
	public ResponseEntity<Wallet> createWallet(@PathVariable Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		Wallet wallet = walletService.createWallet(user);
		return ResponseEntity.ok(wallet);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Wallet> getWallet(@PathVariable Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		Wallet wallet = walletService.getWalletByUser(user);
		return ResponseEntity.ok(wallet);
	}

	@PostMapping("/{userId}/transaction")
	public ResponseEntity<WalletTransaction> createTransaction(@PathVariable Long userId,
			@RequestBody WalletTransaction transaction) {
		User user = userRepository.findById(userId).orElseThrow();
		Wallet wallet = walletService.getWalletByUser(user);
		BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
		if (transaction.getType().name().equalsIgnoreCase("ADD_MONEY")) {
			walletService.updateBalance(wallet, amount);
		} else {
			walletService.updateBalance(wallet, amount.negate());
		}
		WalletTransaction savedTransaction = walletTransactionService.createTransaction(wallet, transaction);
		return ResponseEntity.ok(savedTransaction);
	}

	@GetMapping("/{userId}/transactions")
	public ResponseEntity<List<WalletTransaction>> getAllTransactions(@PathVariable Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		Wallet wallet = walletService.getWalletByUser(user);
		return ResponseEntity.ok(walletTransactionService.getTransactions(wallet));
	}

	@PutMapping("/payorder/{userId}/{orderId}")
	public ResponseEntity<Wallet> payOrder(@PathVariable Long userId, @PathVariable Long orderId) {
		try {
			User user = userRepository.findById(userId).orElseThrow();
			Order order = orderRepository.findById(orderId).orElseThrow();
			Wallet updatedWallet = walletService.payOrderPayment(order, user);
			return ResponseEntity.ok(updatedWallet);
		} catch (WalletException we) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}

package com.crypto.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.domain.WalletTransactionType;
import com.crypto.exception.UserException;
import com.crypto.exception.WalletException;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.OrderRepository;
import com.crypto.repository.UserRepository;
import com.crypto.service.PaymentService;
import com.crypto.service.WalletService;
import com.crypto.service.WalletTransactionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

	private final WalletService walletService;

	private final UserRepository userRepository;

	private final WalletTransactionService walletTransactionService;

	private final OrderRepository orderRepository;
	private final PaymentService paymentService;

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
		BigDecimal amount = transaction.getAmount();
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
	public ResponseEntity<Wallet> payOrder(@PathVariable Long userId, @PathVariable Long orderId) throws Exception {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID " + userId));
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with order ID " + orderId));
		Wallet updatedWallet = walletService.payOrderPayment(order, user);
		return ResponseEntity.ok(updatedWallet);

	}

	@PostMapping("{userId}/topup/create-order")
	public ResponseEntity<String> createTopUpOrder(@PathVariable Long userId, @RequestParam Long amount)
			throws Exception {
		if (amount <= 0) {
			throw new WalletException("Amount must be greater than 0");
		}
		String orderId = paymentService.createRazorpayOrder(userId, amount);
		return new ResponseEntity<String>(orderId, HttpStatus.CREATED);
	}

	@PostMapping("{userId}/topup/success")
	public ResponseEntity<Wallet> handlePaymentSuccess(@PathVariable Long userId,
			@RequestParam String razorpay_order_id, @RequestParam String razorpay_payment_id,
			@RequestParam String razorpay_signature) throws Exception {
		Wallet updatedWallet = paymentService.processPaymentSuccess(razorpay_order_id, razorpay_payment_id,
				razorpay_signature, userId);
		return ResponseEntity.ok(updatedWallet);
	}

	@PostMapping("{userId}/withdraw")
	public ResponseEntity<WalletTransaction> simulateWithdraw(@PathVariable Long userId,
			@RequestParam BigDecimal amount, @RequestParam String accountNumber, @RequestParam String ifscCode,
			@RequestParam String accountHolderName, @RequestParam String bankName) throws Exception {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new WalletException("Withdrawal amount must be greater than 0");
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID " + userId));
		Wallet wallet = walletService.getWalletByUser(user);
		if (wallet.getBalance().compareTo(amount) < 0) {
			throw new WalletException("Insufficient balance for withdrawal");
		}
		BigDecimal newBalance = wallet.getBalance().subtract(amount);
		wallet.setBalance(newBalance);
		walletService.updateBalance(wallet, newBalance);
		WalletTransaction transaction = new WalletTransaction();
		transaction.setWallet(wallet);
		transaction.setType(WalletTransactionType.WITHDRAWAL);
		transaction.setAmount(amount.negate());
		transaction.setDateTime(LocalDateTime.now());
		transaction.setTransferId("WITHDRAW_" + System.currentTimeMillis());
		transaction.setPurpose("Wallet withdrawal simulation");
		transaction.setAccountNumber(accountNumber);
		transaction.setIfscCode(ifscCode);
		transaction.setAccountHolderName(accountHolderName);
		transaction.setBankName(bankName);
		WalletTransaction savedTransaction = walletTransactionService.createTransaction(wallet, transaction);
		return ResponseEntity.ok(savedTransaction);
	}

}

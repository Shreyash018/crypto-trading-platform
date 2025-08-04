package com.crypto.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.crypto.domain.WalletTransactionType;
import com.crypto.exception.UserException;
import com.crypto.exception.WalletException;
import com.crypto.model.User;
import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.UserRepository;
import com.crypto.repository.WalletRepository;
import com.crypto.repository.WalletTransactionsRepository;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentServiceImpl implements PaymentService {

	private final WalletRepository walletRepository;
	private final UserRepository userRepository;
	private final WalletTransactionsRepository walletTransactionsRepository;
	private final RazorpayClient razorpayClient;
	private final String secretKey;

	public PaymentServiceImpl(UserRepository userRepository, WalletTransactionsRepository walletTransactionsRepository,
			WalletRepository walletRepository, @Value("${razorpay.key.id}") String keyId,
			@Value("${razorpay.key.secret}") String keySecret) throws RazorpayException {
		this.walletRepository = walletRepository;
		this.userRepository = userRepository;
		this.walletTransactionsRepository = walletTransactionsRepository;
		this.razorpayClient = new RazorpayClient(keyId, keySecret);
		this.secretKey = keySecret;
	}

	@Override
	public String createRazorpayOrder(Long userId, Long amount) throws Exception {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID " + userId));
		if (amount <= 0) {
			throw new WalletException("Amount must be greater than 0");
		}
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount * 100); // Amount in paise(Multiply by 100 to convert it into rupees)
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "wallet_topup_" + userId + "_" + System.currentTimeMillis());
		Order order = razorpayClient.orders.create(orderRequest);
		return order.get("id");
	}

	@Override
	public Wallet processPaymentSuccess(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature,
			Long userId) throws Exception {
		JSONObject options = new JSONObject();
		options.put("razorpay_order_id", razorpayOrderId);
		options.put("razorpay_payment_id", razorpayPaymentId);
		options.put("razorpay_signature", razorpaySignature);
		boolean isValid = Utils.verifyPaymentSignature(options, secretKey);
		if (!isValid) {
			throw new RazorpayException("Invalid payment signature");
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID " + userId));
		Wallet wallet = walletRepository.findByUser(user);
		if (wallet == null) {
			throw new WalletException("Wallet not found");
		}
		Payment payment = razorpayClient.payments.fetch(razorpayPaymentId);
		Long amountPaise = payment.get("amount");
		BigDecimal amount = BigDecimal.valueOf(amountPaise).divide(BigDecimal.valueOf(100));
		wallet.setBalance(wallet.getBalance().add(amount));
		walletRepository.save(wallet);
		WalletTransaction transaction = new WalletTransaction();
		transaction.setWallet(wallet);
		transaction.setType(WalletTransactionType.ADD_MONEY);
		transaction.setAmount(amount);
		transaction.setDateTime(LocalDateTime.now());
		transaction.setTransferId(razorpayPaymentId);
		transaction.setPurpose("Top-up via RazorPay (Test)");
		transaction.setAccountNumber("FAKE123456789");
		transaction.setIfscCode("RZPY00000000");
		transaction.setBankName("Razorpay Test Bank");
		transaction.setAccountHolderName(user.getFullName());
		walletTransactionsRepository.save(transaction);
		return wallet;
	}

}

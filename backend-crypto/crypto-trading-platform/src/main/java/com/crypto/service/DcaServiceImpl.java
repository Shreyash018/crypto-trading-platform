package com.crypto.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crypto.domain.DcaFrequency;
import com.crypto.dto.DcaResponse;
import com.crypto.exception.UserException;
import com.crypto.model.Coin;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.OrderRepository;
import com.crypto.repository.UserRepository;
import com.crypto.repository.WalletTransactionsRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class DcaServiceImpl implements DcaService {
	private final UserRepository userRepository;
	private final OrderService orderService;
	private final CoinService coinService;
	private final WalletService walletService;
	private final WalletTransactionsRepository walletTransactionsRepository;
	private final OrderRepository orderRepository;

	@Override
	@Transactional
	public DcaResponse createDcaPlan(Long userId, String coinId, BigDecimal amount, DcaFrequency frequency) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID " + userId));
		try {
			coinService.findById(coinId);
		} catch (Exception e) {
			throw new UserException("Invalid coin ID " + coinId);
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new UserException("DCA amount must be greater than 0");
		}
		if (frequency == null) {
			throw new UserException("DCA frequency cannot be null");
		}
		user.setDcaEnabled(true);
		user.setDcaCoinId(coinId);
		user.setDcaAmount(amount);
		user.setDcaFrequency(frequency);
		user.setNextDcaExecution(calculateNextExecution(frequency));
		user.setLastDcaExecution(null);
		User savedUser = userRepository.save(user);
		log.info("DCA plan created for user {} - Coin: {}, Amount: {}, Frequency: {}", user.getId(), coinId, amount,
				frequency);
		return DcaResponse.from(savedUser);
	}

	@Override
	@Transactional
	public void pauseDcaPlan(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID: " + userId));
		if (!user.isDcaEnabled()) {
			throw new UserException("DCA plan is not active for this user");
		}
		user.setDcaEnabled(false);
		userRepository.save(user);
		log.info("DCA plan paused for user {}", userId);

	}

	@Override
	@Transactional
	public void resumeDcaPlan(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID: " + userId));
		if (user.getDcaCoinId() == null || user.getDcaFrequency() == null) {
			throw new UserException("No DCA plans found for user");
		}
		user.setDcaEnabled(true);
		user.setNextDcaExecution(calculateNextExecution(user.getDcaFrequency()));
		userRepository.save(user);
		log.info("DCA plan resumed for user {}", userId);

	}

	@Override
	public DcaResponse getDcaPlan(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID: " + userId));
		return DcaResponse.from(user);
	}

	@Override
	public List<DcaResponse> getAllActiveDcaPlans() {
		List<User> usersWithDca = userRepository.findAll().stream()
				.filter(user -> user.isDcaEnabled() && user.getDcaCoinId() != null).collect(Collectors.toList());
		return usersWithDca.stream().map(DcaResponse::from).collect(Collectors.toList());
	}

	@Scheduled(fixedRate = 300000)
	@Override
	@Transactional
	public void executeDcaPlans() {
		log.info("Checking for DCA plans ready for execution...");
		List<User> readyUsers = userRepository.findAll().stream().filter(User::isDcaDue).collect(Collectors.toList());
		log.info("Found {} DCA plans ready for execution", readyUsers.size());
		for (User user : readyUsers) {
			try {
				executeDcaForUser(user);
			} catch (Exception e) {
				log.error("Failed to execute DCA for user {}: {}", user.getId(), e.getMessage());
			}
		}

	}

	@Transactional
	private void executeDcaForUser(User user) throws Exception {
		try {
			Coin coin = coinService.findById(user.getDcaCoinId());
			var wallet = walletService.getWalletByUser(user);
			if (wallet.getBalance().compareTo(user.getDcaAmount()) < 0) {
				log.warn("Insufficient balance for DCA execution for user {}. Required: {}, Available: {}",
						user.getId(), user.getDcaAmount(), wallet.getBalance());
				user.setNextDcaExecution(LocalDateTime.now().plusHours(1));
				userRepository.save(user);
				return;
			}
			BigDecimal coinPrice = BigDecimal.valueOf(coin.getCurrentPrice());
			int quantityToBuy = user.getDcaAmount().divide(coinPrice, 0, RoundingMode.DOWN).intValue();
			if (quantityToBuy <= 0) {
				log.warn("Cannot buy any coins with DCA amount {} at current price {} for user {}", user.getDcaAmount(),
						coinPrice, user.getId());
				user.setNextDcaExecution(calculateNextExecution(user.getDcaFrequency()));
				userRepository.save(user);
				return;
			}
			Order dcaOrder = orderService.buyCoin(coin, quantityToBuy, user);
			dcaOrder.setDcaOrder(true);
			orderRepository.save(dcaOrder);
			user.setLastDcaExecution(LocalDateTime.now());
			user.setNextDcaExecution(calculateNextExecution(user.getDcaFrequency()));
			userRepository.save(user);
			markRecentTransactionAsDca(user);
			log.info("Successfully executed DCA for user {} - bought {} {} for {}", user.getId(), quantityToBuy,
					coin.getSymbol(), user.getDcaAmount());
		} catch (Exception e) {
			log.error("Error executing DCA for user {}: {}", user.getId(), e.getMessage());
			user.setNextDcaExecution(LocalDateTime.now().plusHours(1));
			userRepository.save(user);
			throw e;
		}

	}

	private LocalDateTime calculateNextExecution(DcaFrequency dcaFrequency) {
		LocalDateTime now = LocalDateTime.now();
		switch (dcaFrequency) {
		case DAILY:
			return now.plusDays(1);
		case WEEKLY:
			return now.plusWeeks(1);
		case MONTHLY:
			return now.plusMonths(1);
		case QUARTERLY:
			return now.plusMonths(3);
		case YEARLY:
			return now.plusYears(1);

		default:
			return now.plusDays(1);
		}
	}

	private void markRecentTransactionAsDca(User user) {
		try {
			var wallet = walletService.getWalletByUser(user);
			var recentTransactions = walletTransactionsRepository.findByWallet(wallet);
			if (!recentTransactions.isEmpty()) {
				WalletTransaction latestTransaction = recentTransactions.get(recentTransactions.size() - 1);
				latestTransaction.setDcaTransaction(true);
				walletTransactionsRepository.save(latestTransaction);
			}
		} catch (Exception e) {
			log.error("Failed to mark transaction as DCA for user {}: {}", user.getId(), e.getMessage());
		}

	}

	@Override
	@Transactional
	public void deleteDcaPlan(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with ID: " + userId));
		user.setDcaEnabled(false);
		user.setDcaCoinId(null);
		user.setDcaAmount(null);
		user.setDcaFrequency(null);
		user.setNextDcaExecution(null);
		user.setLastDcaExecution(null);
		userRepository.save(user);
		log.info("DCA plan deleted for user {}", userId);

	}

}

package com.crypto.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.crypto.domain.OrderType;
import com.crypto.domain.WalletTransactionType;
import com.crypto.exception.WalletException;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.WalletRepository;
import com.crypto.repository.WalletTransactionsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
	private final WalletRepository walletRepository;
	private final WalletTransactionsRepository walletTransactionsRepository;

	@Override
	public Wallet createWallet(User user) {
		Wallet existingWallet = walletRepository.findByUser(user);
		if (existingWallet != null) {
			return existingWallet;
		}
		Wallet wallet = new Wallet();
		wallet.setUser(user);
		wallet.setBalance(BigDecimal.ZERO);
		return walletRepository.save(wallet);
	}

	@Override
	public Wallet getWalletByUser(User user) {
		return walletRepository.findByUser(user);
	}

	@Override
	public void updateBalance(Wallet wallet, BigDecimal amount) {
		wallet.setBalance(amount);
		walletRepository.save(wallet);
	}

	@Override
	public Wallet payOrderPayment(Order order, User user) throws WalletException {
		Wallet wallet = getWalletByUser(user);
		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setWallet(wallet);
		walletTransaction.setPurpose(order.getOrderType() + " " + order.getCoin().getId());
		walletTransaction.setDateTime(LocalDateTime.now());
		walletTransaction.setTransferId(order.getCoin().getSymbol());
		if (order.getOrderType().equals(OrderType.BUY)) {
			walletTransaction.setType(WalletTransactionType.BUY_ASSET);
			walletTransaction.setAmount(order.getPrice().negate());
			BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
			if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
				throw new WalletException("Insufficient funds for this transaction");
			}
			wallet.setBalance(newBalance);
		} else if (order.getOrderType().equals(OrderType.SELL)) {
			walletTransaction.setType(WalletTransactionType.SELL_ASSET);
			walletTransaction.setAmount(order.getPrice());
			BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
			wallet.setBalance(newBalance);
		}
		walletTransactionsRepository.save(walletTransaction);
		walletRepository.save(wallet);
		return wallet;
	}

}

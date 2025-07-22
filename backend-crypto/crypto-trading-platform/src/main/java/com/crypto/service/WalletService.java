package com.crypto.service;

import java.math.BigDecimal;

import com.crypto.exception.WalletException;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.model.Wallet;

public interface WalletService {
	Wallet createWallet(User user);

	Wallet getWalletByUser(User user);

	void updateBalance(Wallet wallet, BigDecimal amount);

	Wallet payOrderPayment(Order order, User user) throws WalletException;

}

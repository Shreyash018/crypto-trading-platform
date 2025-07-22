package com.crypto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.WalletTransactionsRepository;

public class WalletTransactionServiceImpl implements WalletTransactionService {
	@Autowired
	private WalletTransactionsRepository walletTransactionsRepository;

	@Override
	public List<WalletTransaction> getTransactions(Wallet wallet) {
		return walletTransactionsRepository.findByWallet(wallet);
	}

	@Override
	public WalletTransaction createTransaction(Wallet wallet, WalletTransaction transaction) {
		transaction.setWallet(wallet);
		return walletTransactionsRepository.save(transaction);
	}

}

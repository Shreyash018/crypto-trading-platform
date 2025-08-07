package com.crypto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;
import com.crypto.repository.WalletTransactionsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class WalletTransactionServiceImpl implements WalletTransactionService {

	private final WalletTransactionsRepository walletTransactionsRepository;

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

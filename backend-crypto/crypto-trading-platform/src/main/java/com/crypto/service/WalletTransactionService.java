package com.crypto.service;

import java.util.List;

import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;

public interface WalletTransactionService {
	List<WalletTransaction> getTransactions(Wallet wallet);

	WalletTransaction createTransaction(Wallet wallet, WalletTransaction transaction);

}

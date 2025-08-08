package com.crypto.service;

import java.util.List;

import com.crypto.domain.WalletTransactionType;
import com.crypto.model.Wallet;
import com.crypto.model.WalletTransaction;

public interface WalletTransactionService {
	  WalletTransaction createTransaction(Wallet wallet,
              WalletTransactionType type,
              String transferId,
              String purpose,
              Long amount
);

List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);

}

package com.crypto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.WalletTransaction;
import com.crypto.model.Wallet;

public interface WalletTransactionsRepository extends JpaRepository<WalletTransaction, Long> {
	List<WalletTransaction> findByWallet(Wallet wallet);

}

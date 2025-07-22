package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.User;
import com.crypto.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	Wallet findByUser(User user);

}

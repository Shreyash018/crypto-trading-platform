package com.crypto.service;

import com.crypto.exception.WalletException;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.model.Wallet;

public interface WalletService {
	 Wallet getUserWallet(User user) throws WalletException;

	    public Wallet addBalanceToWallet(Wallet wallet, Long money) throws WalletException;

	    public Wallet findWalletById(Long id) throws WalletException;

	    public Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws WalletException;

	    public Wallet payOrderPayment(Order order, User user) throws WalletException;
}

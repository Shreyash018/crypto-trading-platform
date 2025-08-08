package com.crypto.service;

import java.util.List;

import com.crypto.model.User;
import com.crypto.model.Withdrawal;

public interface WithdrawalService {
	 Withdrawal requestWithdrawal(Long amount,User user);
	    Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception;
	    List<Withdrawal> getUsersWithdrawalHistory(User user);
	    List<Withdrawal> getAllWithdrawalRequest();
}

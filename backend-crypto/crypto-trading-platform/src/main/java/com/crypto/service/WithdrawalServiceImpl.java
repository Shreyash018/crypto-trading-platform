package com.crypto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crypto.domain.WithdrawalStatus;
import com.crypto.model.User;
import com.crypto.model.Withdrawal;
import com.crypto.repository.WithdrawalRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {
	
	 private final WithdrawalRepository withdrawalRepository;


	    @Override
	    public Withdrawal requestWithdrawal(Long amount,User user) {
	        Withdrawal withdrawal=new Withdrawal();
	        withdrawal.setAmount(amount);
	        withdrawal.setStatus(WithdrawalStatus.PENDING);
	        withdrawal.setDate(LocalDateTime.now());
	        withdrawal.setUser(user);
	        return withdrawalRepository.save(withdrawal);
	    }

	    @Override
	    public Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception {
	        Optional<Withdrawal> withdrawalOptional=withdrawalRepository.findById(withdrawalId);

	        if(withdrawalOptional.isEmpty()){
	            throw new Exception("withdrawal id is wrong...");
	        }

	        Withdrawal withdrawal=withdrawalOptional.get();


	        withdrawal.setDate(LocalDateTime.now());

	        if(accept){
	            withdrawal.setStatus(WithdrawalStatus.SUCCESS);
	        }
	        else{
	            withdrawal.setStatus(WithdrawalStatus.DECLINE);
	        }

	        return withdrawalRepository.save(withdrawal);
	    }

	    @Override
	    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
	        return withdrawalRepository.findByUserId(user.getId());
	    }

	    @Override
	    public List<Withdrawal> getAllWithdrawalRequest() {
	        return withdrawalRepository.findAll();
	    }
}

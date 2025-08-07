package com.crypto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.crypto.domain.DcaFrequency;
import com.crypto.model.User;

import lombok.Data;

@Data
public class DcaResponse {
	private Long userId;
	private String userEmail;
	private boolean dcaEnabled;
	private String dcaCoinId;
	private BigDecimal dcaAmount;
	private DcaFrequency dcaFrequency;
	private LocalDateTime nextExecution;
	private LocalDateTime lastExecution;

	public static DcaResponse from(User user) {
		DcaResponse response = new DcaResponse();
		response.setUserId(user.getId());
		response.setUserEmail(user.getEmail());
		response.setDcaEnabled(user.isDcaEnabled());
		response.setDcaCoinId(user.getDcaCoinId());
		response.setDcaAmount(user.getDcaAmount());
		response.setDcaFrequency(user.getDcaFrequency());
		response.setNextExecution(user.getNextDcaExecution());
		response.setLastExecution(user.getLastDcaExecution());
		return response;
	}

}

package com.crypto.service;

import java.math.BigDecimal;
import java.util.List;

import com.crypto.domain.DcaFrequency;
import com.crypto.dto.DcaResponse;

public interface DcaService {
	DcaResponse createDcaPlan(Long userId, String coinId, BigDecimal amount, DcaFrequency frequency);

	void pauseDcaPlan(Long userId);

	void resumeDcaPlan(Long userId);

	void deleteDcaPlan(Long userId);

	DcaResponse getDcaPlan(Long userId);

	List<DcaResponse> getAllActiveDcaPlans();

	void executeDcaPlans();

}

package com.crypto.dto;

import java.math.BigDecimal;

import com.crypto.domain.DcaFrequency;

import lombok.Data;

@Data
public class DcaCreateRequest {
	private Long userId;
	private String coinId;
	private BigDecimal amount;
	private DcaFrequency frequency;

}

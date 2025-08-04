package com.crypto.dto;

import com.crypto.domain.VerificationType;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
	  private String sendTo;
	  private VerificationType verificationType;
}

package com.crypto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
	@Email
	@NotNull
	private String email;

}

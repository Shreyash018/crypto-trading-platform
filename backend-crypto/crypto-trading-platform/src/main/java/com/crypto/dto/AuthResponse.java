package com.crypto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthResponse {

	private String jwt;
	private String message;
	private boolean status;
	private boolean twoFactorAuthEnabled;
	private String session;
	
}

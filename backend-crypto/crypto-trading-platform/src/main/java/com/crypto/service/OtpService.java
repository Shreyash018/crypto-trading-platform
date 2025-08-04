package com.crypto.service;

public interface OtpService {

	 public void generateOtp(String email) ;
	 
	 public boolean validateOtp(String email, String otp); 
}

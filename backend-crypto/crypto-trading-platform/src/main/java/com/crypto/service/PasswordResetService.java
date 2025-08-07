package com.crypto.service;

public interface PasswordResetService {
	void initiateForgotPassword(String email);

	void resetPassword(String email, String otp, String newPassword);

}

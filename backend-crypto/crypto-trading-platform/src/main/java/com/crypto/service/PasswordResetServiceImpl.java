package com.crypto.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.crypto.exception.UserException;
import com.crypto.model.User;
import com.crypto.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
	private final UserRepository userRepository;
	private final OtpService otpService;
	private final UserService userService;

	@Override
	public void initiateForgotPassword(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String resetToken = UUID.randomUUID().toString();
			user.setResetToken(resetToken);
			user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
			userRepository.save(user);
			otpService.generateOtp(email);
			System.out.println("---*--- PASSWORD RESET DEBUG INFO ---*---");
			System.out.println("Reset token for " + email + ": " + resetToken);
			System.out.println("Token expires at: " + user.getResetTokenExpiry());
			System.out.println("OTP sent to email via OtpService");
			System.out.println("======== ======== ========");
		}

	}

	@Override
	public void resetPassword(String email, String otp, String newPassword) {
		boolean isOtpValid = otpService.validateOtp(email, otp);
		if (!isOtpValid) {
			throw new UserException("Invalid or expired otp. Please request a new password reset.");
		}
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("Unable to reset password. Please try again.");
		}
		if (user.getResetToken() == null || user.isResetTokenExpired()) {
			throw new UserException("Password reset session has expired. Please request a new password reset.");
		}
		userService.updatePassword(user, newPassword);
		user.setResetToken(null);
		user.setResetTokenExpiry(null);
		userRepository.save(user);

	}

}

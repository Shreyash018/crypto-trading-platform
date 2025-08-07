package com.crypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.dto.ForgotPasswordRequest;
import com.crypto.dto.ResetPasswordRequest;
import com.crypto.service.PasswordResetService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class PasswordResetController {
	private final PasswordResetService passwordResetService;

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
		passwordResetService.initiateForgotPassword(request.getEmail());
		return ResponseEntity.ok("If an account with this email exists, password reset instructionshave been sent.");
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
		passwordResetService.resetPassword(request.getEmail(), request.getOtp(), request.getPassword());
		return ResponseEntity.ok("Password has been reset successfully. You can login now with your new password.");
	}

}

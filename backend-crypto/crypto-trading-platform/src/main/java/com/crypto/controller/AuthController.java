package com.crypto.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.dto.LoginReqDto;
import com.crypto.exception.UserException;
import com.crypto.model.User;
import com.crypto.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final UserRepository userRepository;

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody() @Valid User user) throws UserException {

		User isEmailExist = userRepository.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new UserException("Email Is Already Used With Another Account");
		}

		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setMobile(user.getMobile());
		createdUser.setPassword(user.getPassword());

		User savedUser = userRepository.save(createdUser);
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody @Valid LoginReqDto loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail());

		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		if (!user.getPassword().equals(loginRequest.getPassword())) {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/login/google")
	public void redirectToGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Redirect to the Google OAuth2 authorization URI
		response.sendRedirect("/login/oauth2/authorization/google");
	}

}

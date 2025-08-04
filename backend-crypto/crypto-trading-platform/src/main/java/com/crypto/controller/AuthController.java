package com.crypto.controller;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.config.JwtProvider;
import com.crypto.domain.VerificationType;
import com.crypto.dto.ApiResponse;
import com.crypto.dto.AuthResponse;
import com.crypto.dto.LoginReqDto;
import com.crypto.dto.OtpVerificationReq;
import com.crypto.exception.UserException;
import com.crypto.model.User;
import com.crypto.repository.UserRepository;
import com.crypto.service.CustomeUserServiceImplementation;
import com.crypto.service.OtpService;
import com.crypto.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Validated
public class AuthController {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserService userService;
	
	private final OtpService otpService;
	
	private final CustomeUserServiceImplementation customeUserServiceImplementation;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody() @Valid User user) throws UserException {

		User isEmailExist = userRepository.findByEmail(user.getEmail());

		if (isEmailExist != null) {
			throw new UserException("Email Is Already Used With Another Account");
		}

		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setMobile(user.getMobile());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		// Creates a Spring Security Authentication object.

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = JwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setStatus(true);
		authResponse.setMessage("Register Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginReqDto loginRequest) {
		
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		System.out.println(username + " ----- " + password);

		Authentication authentication = authenticate(username, password);
		
		User user= userService.findUserByEmail(username);
		
        SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = JwtProvider.generateToken(authentication);
		
		if (user.getTwoFactorAuth() != null && user.getTwoFactorAuth().isEnabled()) {
	        otpService.generateOtp(username); // Store in DB or cache
	        AuthResponse response = new AuthResponse();
	        response.setTwoFactorAuthEnabled(true);
	        response.setMessage("Two-factor authentication enabled. OTP has been sent.");
	        response.setStatus(true);
	        return ResponseEntity.ok(response); // Do not return JWT yet
	    }
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setStatus(true);
		authResponse.setMessage("Login Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}
	
	
	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = customeUserServiceImplementation.loadUserByUsername(username);

		System.out.println("sign in userDetails - " + userDetails);

		if (userDetails == null) {
			System.out.println("sign in userDetails - null " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("sign in userDetails - password not match " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<AuthResponse> verifyOtp(@RequestBody OtpVerificationReq request) {
	    boolean isValid = otpService.validateOtp(request.getEmail(), request.getOtp());

	    if (!isValid) {
	        throw new RuntimeException("Invalid OTP"); // Use proper exception handling
	    }

	    User user = userService.findUserByEmail(request.getEmail());

	    Authentication authentication = new UsernamePasswordAuthenticationToken(
	        user.getEmail(), null, Collections.emptyList());

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String jwt = JwtProvider.generateToken(authentication);

	    AuthResponse response = new AuthResponse();
	    response.setJwt(jwt);
	    response.setMessage("OTP verified. Login success");
	    response.setStatus(true);
	    response.setTwoFactorAuthEnabled(false);
	    return ResponseEntity.ok(response);
	}

	@PostMapping("/api/users/enable-2fa")
	public ResponseEntity<ApiResponse> enable2FA(@RequestHeader("Authorization") String jwt,
	                                             @RequestParam VerificationType verificationType) {
	    User user = userService.findUserProfileByJwt(jwt);

	    if (verificationType == VerificationType.EMAIL) {
	        otpService.generateOtp(user.getEmail());
	        // Optionally send email
	    }

	    user.getTwoFactorAuth().setVerificationType(verificationType);
	    userRepository.save(user);

	    return ResponseEntity.status(HttpStatus.OK)
	    	    .body(new ApiResponse(LocalDateTime.now(), "OTP sent to enable 2FA", HttpStatus.OK.value()));
	}


//	@GetMapping("/login/google")
//	public void redirectToGoogle(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		// Redirect to the Google OAuth2 authorization URI
//		response.sendRedirect("/login/oauth2/authorization/google");
//	}

}

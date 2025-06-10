package com.crypto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.exception.UserException;
import com.crypto.model.User;
import com.crypto.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor 
public class AuthController {

	private final UserRepository userRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody() User user) throws UserException{
		
		User isEmailExist = userRepository.findByEmail(user.getEmail());
		
		if(isEmailExist!=null) { 
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
	
}

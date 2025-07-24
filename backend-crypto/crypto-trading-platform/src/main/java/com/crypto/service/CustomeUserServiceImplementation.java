package com.crypto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crypto.model.User;
import com.crypto.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomeUserServiceImplementation implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //username is usually an email or username sent during login.
		                                                                                      //It expects an object of type UserDetails which contains credentials + authorities.

        User user = userRepository.findByEmail(username);
		
		if(user==null) {

			throw new UsernameNotFoundException("user not found with email  - "+username);
		}
		
		List<GrantedAuthority> authorities=new ArrayList<>();

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),user.getPassword(),authorities);
		//this wraps your User model into Spring’s User class (which implements UserDetails).
		//authorities represents roles or permissions (empty in your case but can be populated from user roles)
	}

}

//This class is a custom implementation of Spring Security’s UserDetailsService interface.
//It allows Spring Security to fetch user details (like email, password, roles) from your database, not from an in-memory store.
//Spring calls this method automatically when a login attempt is made.username is usually the email from the login request (e.g., form or API).
//Login – to authenticate and issue token
//Token validation – e.g., parsing JWT to get username, and calling this method to load user info for SecurityContext


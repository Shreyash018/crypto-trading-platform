package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 User findByEmail(String email);
}

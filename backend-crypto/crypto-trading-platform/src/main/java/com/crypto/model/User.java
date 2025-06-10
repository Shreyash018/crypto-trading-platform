package com.crypto.model;

import com.crypto.domain.USER_ROLE;
import com.crypto.domain.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;
	
	@Column(unique = true, nullable = false)
	private String email;
	private String mobile;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // not included in responses (JSON output).
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status= UserStatus.PENDING;

	private boolean isVerified = false;

	@Embedded
	private TwoFactorAuth twoFactorAuth= new TwoFactorAuth();

	@Enumerated(EnumType.STRING)
	private USER_ROLE role= USER_ROLE.ROLE_USER;

}

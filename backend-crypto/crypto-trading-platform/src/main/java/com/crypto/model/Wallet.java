package com.crypto.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallets", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private User user;

	private BigDecimal balance = BigDecimal.ZERO;

}

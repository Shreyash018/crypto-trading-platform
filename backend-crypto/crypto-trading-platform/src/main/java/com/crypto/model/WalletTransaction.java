package com.crypto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.crypto.domain.WalletTransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data

public class WalletTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Wallet wallet;

	@Enumerated
	private WalletTransactionType type;

	private LocalDateTime dateTime;

	private String transferId;

	private String purpose;

	private BigDecimal amount;
	private String accountNumber;
	private String accountHolderName;
	private String ifscCode;
	private String bankName;
	private boolean isDcaTransaction = false;

}

package com.crypto.model;

import java.time.LocalDateTime;

import com.crypto.domain.WithdrawalStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data

public class Withdrawal {
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Id
	    private Long id;

	    private WithdrawalStatus status;

	    private Long amount;

	    @ManyToOne
	    private User user;

	    private LocalDateTime date;
}

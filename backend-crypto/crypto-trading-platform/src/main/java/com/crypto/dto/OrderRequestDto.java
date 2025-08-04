package com.crypto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
	@NotNull(message = "User ID is required")
	private Long userId;
	@NotNull(message = "Coin ID is required")
	private String coinId;
	@Min(value = 1, message = "quantity must be at least one")
	private int quantity;

}

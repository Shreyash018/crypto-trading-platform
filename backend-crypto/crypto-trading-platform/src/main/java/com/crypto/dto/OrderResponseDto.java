package com.crypto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.crypto.domain.OrderStatus;
import com.crypto.domain.OrderType;
import com.crypto.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponseDto {
	private Long id;
	private Long userId;
	private String coinId;
	private String coinSymbol;
	private OrderType orderType;
	private OrderStatus orderStatus;
	private BigDecimal price;
	private int quantity;
	private LocalDateTime timestamp;

	public static OrderResponseDto from(Order o) {
		return new OrderResponseDto(o.getId(), o.getUser().getId(), o.getCoin().getId(), o.getCoin().getSymbol(),
				o.getOrderType(), o.getStatus(), o.getPrice(), (int) o.getQuantity(), o.getTimestamp());
	}

}

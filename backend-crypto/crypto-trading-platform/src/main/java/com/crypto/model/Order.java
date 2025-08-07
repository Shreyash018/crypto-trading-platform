package com.crypto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.crypto.domain.OrderStatus;
import com.crypto.domain.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Order {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    private User user;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private OrderType orderType;

	    @Column(nullable = false)
	    private BigDecimal price;

	    @Column(nullable = false)
	    private LocalDateTime timestamp;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private OrderStatus status = OrderStatus.PENDING;

	    @ManyToOne
	    @JoinColumn(name = "coin_id", nullable = false)
	    private Coin coin;

	    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
	    private double quantity;
	    
	    private double buyPrice;

	    private double sellPrice;
	    private boolean isDcaOrder=false;
	    
}

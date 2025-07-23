package com.crypto.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crypto.domain.OrderStatus;
import com.crypto.domain.OrderType;
import com.crypto.model.Asset;
import com.crypto.model.Coin;
import com.crypto.model.Order;
import com.crypto.model.User;
import com.crypto.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	public final OrderRepository orderRepository;
	private final AssetService assetService;
	private final WalletService walletService;

	@Override
	@Transactional
	public Order createOrder(User user, Coin coin, int quantity, OrderType orderType) {
		double price = coin.getCurrentPrice() * quantity;

		Order order = new Order();
		order.setUser(user);
		order.setOrderType(orderType);
		order.setPrice(BigDecimal.valueOf(price));
		order.setTimestamp(LocalDateTime.now());
		order.setStatus(OrderStatus.PENDING);

		return orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("Order not found"));
		return order;
	}

	@Override
	public List<Order> getAllOrdersForUser(Long userId) {
		List<Order> allUserOrders = orderRepository.findByUserId(userId);
		return allUserOrders;
	}

	@Override
	@Transactional
	public void cancelOrder(Long orderId) {
		Order order = getOrderById(orderId);

		if (order.getStatus() == OrderStatus.PENDING) {
			order.setStatus(OrderStatus.CANCELLED);
			orderRepository.save(order);
		} else {
			throw new IllegalStateException("Cannot cancel order, it is already processed or cancelled.");
		}
	}

	@Override
	@Transactional
	public Order buyCoin(Coin coin, int quantity, User user) throws Exception {

		if (quantity < 0)
			throw new Exception("quantity must be one or greater than one");

		Order order = createOrder(user, coin, quantity, OrderType.BUY);

		walletService.payOrderPayment(order, user);

		order.setStatus(OrderStatus.SUCCESS);
		order.setOrderType(OrderType.BUY);

		Order savedOrder = orderRepository.save(order);

		assetService.addCoin(user.getId(), coin, quantity);

		return savedOrder;
	}

	@Override
	@Transactional
	public Order sellCoin(Coin coin, int quantity, User user) throws Exception {
		Asset asset = assetService.findAssetByUserId(user.getId());

		Optional<Coin> coinOpt = asset.getCoins().stream().filter(c -> c.getSymbol().equalsIgnoreCase(coin.getSymbol()))
				.findFirst();

		if (coinOpt.isEmpty()) {
			throw new Exception("Coin not found in your asset.");
		}

		Coin userCoin = coinOpt.get();

		if (userCoin.getQuantity() < quantity) {
			throw new Exception("Not enough quantity to sell.");
		}

		coin.setQuantity(coin.getQuantity() - quantity);
		
		Order order = createOrder(user, coin, quantity, OrderType.SELL);

		walletService.payOrderPayment(order, user);

		order.setStatus(OrderStatus.SUCCESS);
		order.setOrderType(OrderType.SELL);

		Order savedOrder = orderRepository.save(order);

		assetService.addCoin(user.getId(), coin, quantity);

		return savedOrder;

	}

}

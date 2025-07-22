package com.crypto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crypto.exception.UserException;
import com.crypto.model.Asset;
import com.crypto.model.Coin;
import com.crypto.repository.AssetRepository;
import com.crypto.repository.CoinRepository;
import com.crypto.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssetServiceImpl implements AssetService {

	private final AssetRepository assetRepository;
	private final UserRepository userRepository;
	private final CoinRepository coinRepository;

	@Override
	public void addCoin(Long userId, String coinSymbol, double quantity, double pricePerUnit) throws UserException {

		Asset asset = assetRepository.findByUserId(userId).orElseThrow(() -> new UserException("Asset not found"));
		
		Optional<Coin> existing = asset.getCoins().stream().filter(c -> c.getSymbol().equalsIgnoreCase(coinSymbol)).findFirst();
		
		
	}

	@Override
	public void updateCoin(Long userId, String coinSymbol, double quantity, double pricePerUnit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCoin(Long userId, String coinSymbol) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Coin> getAllCoinsForUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

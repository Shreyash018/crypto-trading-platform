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

	@Override
	public void addCoin(Long userId, Coin coin, double quantity) throws UserException {

		Asset asset = assetRepository.findByUserId(userId).orElseThrow(() -> new UserException("Asset not found"));

		Optional<Coin> existing = asset.getCoins().stream().filter(c -> c.getSymbol().equalsIgnoreCase(coin.getSymbol()))
				.findFirst();

		if (existing.isPresent()) {

			Coin acoin = existing.get();
			double totalQuantity = asset.getQuantity() + quantity;
			double totalCost = (asset.getQuantity() * asset.getBuyPrice()) + (quantity * coin.getCurrentPrice());
			asset.setQuantity(totalQuantity);
			asset.setBuyPrice(totalCost / totalQuantity);

		} else {

			Coin newCoin = new Coin();

			newCoin.setSymbol(coin.getSymbol());
			newCoin.setName(coin.getSymbol().toUpperCase());
			newCoin.setCurrentPrice(coin.getCurrentPrice());
			newCoin.setAsset(asset);

			asset.getCoins().add(newCoin);
			asset.setQuantity(quantity);
			asset.setBuyPrice(coin.getCurrentPrice());
		}

		assetRepository.save(asset);
	}

	@Override
	public void updateCoin(Long userId, String coinSymbol, double quantity, double pricePerUnit) {
		Asset asset = assetRepository.findByUserId(userId)
				.orElseThrow(() -> new UserException("Asset not found for user"));

		Optional<Coin> coinOpt = asset.getCoins().stream().filter(c -> c.getSymbol().equalsIgnoreCase(coinSymbol))
				.findFirst();

		if (coinOpt.isPresent()) {
			Coin coin = coinOpt.get();
			coin.setCurrentPrice(pricePerUnit);
			asset.setQuantity(quantity);
			asset.setBuyPrice(pricePerUnit);
			assetRepository.save(asset); // Persist changes

		} else {

			throw new UserException("Coin not found for this user");
		}
	}

	@Override
	public void deleteCoin(Long userId, String coinSymbol) {
		Asset asset = assetRepository.findByUserId(userId)
				.orElseThrow(() -> new UserException("Asset not found for user"));

		boolean removed = asset.getCoins().removeIf(c -> c.getSymbol().equalsIgnoreCase(coinSymbol));

		if (removed) {
			assetRepository.save(asset); // Persist updated asset
		} else {
			throw new UserException("Coin not found for this user");
		}
	}

	@Override
	public List<Coin> getAllCoinsForUser(Long userId) {
		Asset asset = assetRepository.findByUserId(userId).orElseThrow(() -> new UserException("Invalid User Id"));
		return asset.getCoins();
	}
	
	@Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) throws Exception {
        return assetRepository.findByUserIdAndCoinId(userId,coinId);
    }

}

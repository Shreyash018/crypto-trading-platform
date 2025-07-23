package com.crypto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crypto.exception.UserException;
import com.crypto.model.Asset;
import com.crypto.model.Coin;
import com.crypto.model.User;
import com.crypto.repository.AssetRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssetServiceImpl implements AssetService {

	private final AssetRepository assetRepository;

	@Override
	public Asset createAssetForUser(User user) {
	    Asset asset = new Asset();
	    asset.setUser(user);
	    asset.setCoins(new ArrayList<>());
	    return assetRepository.save(asset);
	}

	
	@Override
	public void addCoin(Long userId, Coin coin, int quantity) throws UserException {

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

//			newCoin.setSymbol(coin.getSymbol());
//			newCoin.setName(coin.getSymbol().toUpperCase());
//			newCoin.setCurrentPrice(coin.getCurrentPrice());
//			newCoin.setAsset(asset);
//
//			asset.getCoins().add(newCoin);
//			asset.setQuantity(quantity);
//			asset.setBuyPrice(coin.getCurrentPrice());
			
			newCoin.setSymbol(coin.getSymbol());
	        newCoin.setName(coin.getSymbol().toUpperCase());
	        newCoin.setQuantity(quantity);
	        
	        newCoin.setCurrentPrice(coin.getCurrentPrice());
	        newCoin.setAsset(asset);

	        asset.setBuyPrice(coin.getCurrentPrice() * quantity);
	        asset.getCoins().add(newCoin);
		}

		assetRepository.save(asset);
	}

	@Override
	public void updateCoin(Long userId, Coin coin, int quantity) {
		Asset asset = assetRepository.findByUserId(userId)
				.orElseThrow(() -> new UserException("Asset not found for user"));

		Optional<Coin> coinOpt = asset.getCoins().stream().filter(c -> c.getSymbol().equalsIgnoreCase(coin.getSymbol()))
				.findFirst();

		if (coinOpt.isPresent()) {
			Coin coins = coinOpt.get();
			coins.setCurrentPrice(coin.getCurrentPrice());
			asset.setQuantity(quantity);
			asset.setBuyPrice(coin.getCurrentPrice()*quantity);
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
    public Asset findAssetByUserId(Long userId) throws Exception {
        return assetRepository.findByUserId(userId).orElseThrow(() -> new UserException("Asset not found for user"));
    }

}

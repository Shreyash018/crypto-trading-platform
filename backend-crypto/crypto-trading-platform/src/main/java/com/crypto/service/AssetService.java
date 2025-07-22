package com.crypto.service;

import java.util.List;

import com.crypto.model.Asset;
import com.crypto.model.Coin;

public interface AssetService {

	    void addCoin(Long userId, Coin coin, double quantity);
	    
	    void updateCoin(Long userId, String coinSymbol, double quantity, double pricePerUnit);
	    
	    void deleteCoin(Long userId, String coinSymbol);
	    
	    List<Coin> getAllCoinsForUser(Long userId);

		Asset findAssetByUserIdAndCoinId(Long userId, String coinId) throws Exception;
	
}

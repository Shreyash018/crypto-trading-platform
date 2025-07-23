package com.crypto.service;

import java.util.List;

import com.crypto.model.Asset;
import com.crypto.model.Coin;
import com.crypto.model.User;

public interface AssetService {

	
	Asset createAssetForUser(User user);
	
	    void addCoin(Long userId, Coin coin, int quantity);
	    
	    void updateCoin(Long userId, Coin coin, int quantity);
	    
	    void deleteCoin(Long userId, String coinSymbol);
	    
	    List<Coin> getAllCoinsForUser(Long userId);

		Asset findAssetByUserId(Long userId) throws Exception;
	
}

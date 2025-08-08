package com.crypto.service;

import java.util.List;

import com.crypto.model.Asset;
import com.crypto.model.Coin;
import com.crypto.model.User;

public interface AssetService {

	
	 Asset createAsset(User user, Coin coin, double quantity);

	    Asset getAssetById(Long assetId);

	    Asset getAssetByUserAndId(Long userId,Long assetId);

	    List<Asset> getUsersAssets(Long userId);

	    Asset updateAsset(Long assetId,double quantity) throws Exception;

	    Asset findAssetByUserIdAndCoinId(Long userId,String coinId) throws Exception;

	    void deleteAsset(Long assetId);

	
}

package com.crypto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	 public List<Asset> findByUserId(Long userId);

	   Asset findByUserIdAndCoinId(Long userId, String coinId);

	   Asset findByIdAndUserId(Long assetId, Long userId);

}

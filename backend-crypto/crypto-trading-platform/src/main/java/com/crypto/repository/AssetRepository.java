package com.crypto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	 Optional<Asset> findByUserId(Long userId);

	Asset findByUserIdAndCoinId(Long userId, String coinId);
}

package com.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}

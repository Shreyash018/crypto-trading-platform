package com.crypto.service;

import java.util.List;

import com.crypto.model.Coin;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CoinService {
	
	List<Coin> getCoinList(int page) throws Exception;
	
	String getMarketChart(String coinId, int days) throws Exception;
	
	String coinDetails(String coinId) throws JsonProcessingException;
	
	Coin findById(String coinId) throws Exception;
	
	String searchCoin(String keyword);
	
	String getTop50CoinsByMarketCap();	
}

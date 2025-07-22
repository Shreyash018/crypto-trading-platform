package com.crypto.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "coins")
public class Coin {

	@Id
	@JsonProperty("id")
	private String id;

	@JsonProperty("symbol")
	private String symbol;

	@JsonProperty("name")
	private String name;

	@JsonProperty("image")
	private String image;

	@JsonProperty("current_price")
	private double currentPrice;

	@JsonProperty("market_cap")
	private long marketCap;

	@JsonProperty("market_cap_rank")
	private int marketCapRank;

	@JsonProperty("high_24h")
	private double high24h;

	@JsonProperty("low_24h")
	private double low24h;

	@JsonProperty("price_change_percentage_24h")
	private double priceChangePercentage24h;

	@JsonProperty("ath")
	private double ath;

	@JsonProperty("atl")
	private double atl;
	
	@ManyToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;

}

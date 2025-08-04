package com.crypto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crypto.model.Coin;
import com.crypto.repository.CoinRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service

public class CoinServiceImpl implements CoinService {

	private final CoinRepository coinRepository;

	private final ObjectMapper objectMapper; // serialize and de-serialize from json to java

	public CoinServiceImpl(CoinRepository coinRepository, ObjectMapper objectMapper) {
		this.coinRepository = coinRepository;
		this.objectMapper = objectMapper;
	}

	@Value("${coingecko.api.key}") // this is the annotation used to inject a value from the application.properties
	private String API_KEY;
	// we are injecting coin geckoapi from application properties
	// But Spring doesn't know how to inject a String into the constructor for that
	// field, even though it knows how to inject the value using @Value.
	// setter based dependency injection not constructor based

	@Override
	public List<Coin> getCoinList(int page) throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;

		RestTemplate restTemplate = new RestTemplate(); // RestTemplate is Spring’s core class for calling RESTful web
														// services.
		// used to call REST APIs (GET, POST, PUT, DELETE

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-cg-demo-api-key", API_KEY);// Creates headers to tell the API: “I expect the response in JSON
													// format.”
		HttpEntity<Void> entity = new HttpEntity<>(headers);// Wrapping in

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		// 1.Makes an HTTP GET request to the url 2. Passes the headers (entity) 3.
		// Expects the response body to be a raw String
		// status code (e.g., 200 OK) , headers , body (JSON string of coin list)

		System.out.println(response.getBody());
		List<Coin> coins = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {
		});
		// This line deserializes a JSON string into a Java object, specifically a
		// List<Coin>, using Jackson's ObjectMapper.
		// TypeReference == Java uses type erasure, which means generic types like
		// List<Coin> are erased at runtime — JVM sees only List.
		// This causes a problem when deserializing collections of generics — Jackson
		// can't figure out the type of elements in the list without extra help
		// by using TypeRererence we can easily deserialize into list<coins> with out it
		// we can't;

		return coins;

	}

	@Override
	public String getMarketChart(String coinId, int days) throws Exception {
		String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-cg-demo-api-key", API_KEY);
		HttpEntity<String> entity = new HttpEntity<>(headers);// Wrapping in

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response.getBody();

	}

	@Override
	public String coinDetails(String coinId) throws JsonProcessingException {
		String baseUrl = "https://api.coingecko.com/api/v3/coins/" + coinId;

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-cg-demo-api-key", API_KEY);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		jsonNode.get("image").get("large");
		System.out.println(jsonNode.get("image").get("large"));
		// Read the JSON text from response.getBody() (a String) //Build a tree
		// representation of the JSON using JsonNode objects.
		// You can now traverse the JSON using jsonNode.get("...") safely ,
		// jsonNode.get("image") Accesses the "image" field of the root node.
		// Returns another JsonNode (which is an ObjectNode). Accesses the "large" field
		// inside "image".Returns a TextNode (i.e., the string node holding the image
		// URL).

		Coin coin = new Coin();

		coin.setId(jsonNode.get("id").asText());
		coin.setSymbol(jsonNode.get("symbol").asText());
		coin.setName(jsonNode.get("name").asText());
		coin.setImage(jsonNode.get("image").get("large").asText());

		JsonNode marketData = jsonNode.get("market_data");

		coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
		coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
		coin.setMarketCapRank(jsonNode.get("market_cap_rank").asInt());
		coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
		coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
		coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
		coin.setAth(marketData.get("ath").get("usd").asDouble());
		coin.setAtl(marketData.get("atl").get("usd").asDouble());

		coinRepository.save(coin);
		return response.getBody();
	}

	@Override
	public Coin findById(String coinId) throws Exception {
		Optional<Coin> optionalCoin = coinRepository.findById(coinId);
		if (optionalCoin.isEmpty())
			throw new Exception("invalid coin id");
		return optionalCoin.get();
	}

	@Override
	public String searchCoin(String keyword) {
		String baseUrl = "https://api.coingecko.com/api/v3/search?query=" + keyword;

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-cg-demo-api-key", API_KEY);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());

		return response.getBody();
	}

	@Override
	public String getTop50CoinsByMarketCap() {
		String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&page=1&per_page=50";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("x-cg-demo-api-key", API_KEY);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response.getBody();

	}

}

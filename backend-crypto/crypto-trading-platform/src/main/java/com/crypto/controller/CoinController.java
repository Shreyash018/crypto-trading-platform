package com.crypto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.model.Coin;
import com.crypto.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/coins")
@AllArgsConstructor
public class CoinController {

	private final CoinService coinService;
	
	private final ObjectMapper objectMapper; 
	
	@GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page) throws Exception {
        List<Coin> coins=coinService.getCoinList(page);
        return new ResponseEntity<>(coins, HttpStatus.OK);
    }
	//This tells Spring to extract the "page" query parameter from the URL.
	//GET /coins?page=2 //Then page = 2. //Spring automatically maps the page parameter from the query string to the int page argument.
	
	// getting coins chart 
	 @GetMapping("/{coinId}/chart")
	    ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,
	                                                         @RequestParam("days")int days) throws Exception {
	    String coins=coinService.getMarketChart(coinId,days);
	    JsonNode jsonNode = objectMapper.readTree(coins);

	         return ResponseEntity.ok(jsonNode);
	    }
	
	 // getting coin details by its id and storing in db 
	@GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws JsonProcessingException {
        String coin=coinService.coinDetails(coinId);
        JsonNode jsonNode = objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }
	
    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws JsonProcessingException {
        String coin=coinService.searchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(coin);
        
        return ResponseEntity.ok(jsonNode);
    }
    
    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinByMarketCapRank() throws JsonProcessingException {
        String coin=coinService.getTop50CoinsByMarketCap();
        JsonNode jsonNode = objectMapper.readTree(coin);
        
        return ResponseEntity.ok(jsonNode);
    }
	
}

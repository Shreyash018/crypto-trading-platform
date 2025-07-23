package com.crypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.model.Coin;
import com.crypto.service.AssetService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/assets")
public class AssetController {

	private final AssetService assetService;
	
//	   @GetMapping("/coin/{coinId}/user")
//	    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
//	            @PathVariable String coinId,
//	            @RequestHeader("Authorization") String jwt
//	    ) throws Exception {
//
//	        User user = userService.findUserProfileByJwt(jwt);
//	        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
//	        return ResponseEntity.ok().body(asset);
//	    }
//	
	
	@PostMapping("/users/{userId}/asset/add")
	public ResponseEntity<String> addCoinToUserAsset(@PathVariable Long userId,
	      @RequestBody Coin coinRequest) {
	    assetService.addCoin(userId, coinRequest , coinRequest.getQuantity());
	    return ResponseEntity.ok("Coin added to asset.");
	}

}

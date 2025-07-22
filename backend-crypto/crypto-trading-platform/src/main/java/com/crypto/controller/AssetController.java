package com.crypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.model.Asset;
import com.crypto.model.User;
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
}

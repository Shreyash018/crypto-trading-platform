package com.crypto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.dto.DcaCreateRequest;
import com.crypto.dto.DcaResponse;
import com.crypto.service.DcaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/dca")
@AllArgsConstructor
public class DcaController {
	private final DcaService dcaService;

	@PostMapping("/create")
	public ResponseEntity<DcaResponse> createDcaPlan(@RequestBody DcaCreateRequest request) {
		DcaResponse response = dcaService.createDcaPlan(request.getUserId(), request.getCoinId(), request.getAmount(),
				request.getFrequency());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("{userId}")
	public ResponseEntity<DcaResponse> getDcaPlan(@PathVariable Long userId) {
		DcaResponse response = dcaService.getDcaPlan(userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	public ResponseEntity<List<DcaResponse>> getAllActivePlans() {
		List<DcaResponse> plans = dcaService.getAllActiveDcaPlans();
		return ResponseEntity.ok(plans);
	}

	@PutMapping("/pause/{userId}")
	public ResponseEntity<String> pauseDcaPlan(@PathVariable Long userId) {
		dcaService.pauseDcaPlan(userId);
		return ResponseEntity.ok("DCA plan paused successfully");
	}

	@PutMapping("/resume/{userId}")
	public ResponseEntity<String> resumeDcaPlan(@PathVariable Long userId) {
		dcaService.resumeDcaPlan(userId);
		return ResponseEntity.ok("DCA plan resumed Successfully");
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteDcaPlan(@PathVariable Long userId) {
		dcaService.deleteDcaPlan(userId);
		return ResponseEntity.ok("DCA plan deleted successfully");
	}

	@PostMapping("/execute-now")
	public ResponseEntity<String> executeNow() {
		dcaService.executeDcaPlans();
		return ResponseEntity.ok("DCA execution triggered manually");
	}

}

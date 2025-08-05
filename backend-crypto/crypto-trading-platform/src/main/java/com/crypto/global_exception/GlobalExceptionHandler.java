package com.crypto.global_exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.crypto.dto.ApiResponse;
import com.crypto.exception.CoinNotFoundException;
import com.crypto.exception.InsufficientAssetException;
import com.crypto.exception.InsufficientFundException;
import com.crypto.exception.OrderException;
import com.crypto.exception.UserException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Thrown when validation on an argument annotated with @Valid fails.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String errorMsg = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst()
				.orElse("Validation failed");
		return new ResponseEntity<>(new ApiResponse(LocalDateTime.now(), errorMsg, HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ApiResponse> handleHttpClientError(HttpClientErrorException ex) {
		return new ResponseEntity<>(
				new ApiResponse(LocalDateTime.now(), "Client Error: " + ex.getMessage(), ex.getStatusCode().value()),
				ex.getStatusCode());
	}

	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<ApiResponse> handleHttpServerError(HttpServerErrorException ex) {
		return new ResponseEntity<>(
				new ApiResponse(LocalDateTime.now(), "Server Error: " + ex.getMessage(), ex.getStatusCode().value()),
				ex.getStatusCode());
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ApiResponse> handleUserException(UserException ex) {
		return new ResponseEntity<>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ApiResponse> handleOrderException(OrderException ex) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CoinNotFoundException.class)
	public ResponseEntity<ApiResponse> handleCoinNotFoundException(CoinNotFoundException ex) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsufficientFundException.class)
	public ResponseEntity<ApiResponse> handleInsufficientFundException(InsufficientFundException ex) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InsufficientAssetException.class)
	public ResponseEntity<ApiResponse> handleInsufficientAssetException(InsufficientAssetException ex) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ApiResponse> handleJsonProcessingException(JsonProcessingException ex) {
		return new ResponseEntity<>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleAllOtherExceptions(Exception ex) {
		return new ResponseEntity<>(
				new ApiResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

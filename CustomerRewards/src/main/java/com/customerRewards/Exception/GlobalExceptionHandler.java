package com.customerRewards.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> customerNotFoundhandler(CustomerNotFoundException ex){
		Map<String, Object> body = new HashMap<>();
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("timestamp",LocalDateTime.now());
		body.put("message",ex.getMessage());
		return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
	}
	
	
}

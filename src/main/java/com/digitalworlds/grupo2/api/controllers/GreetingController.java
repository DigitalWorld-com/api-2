package com.digitalworlds.grupo2.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalworlds.grupo2.api.services.IGreetingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api2")
@AllArgsConstructor
public final class GreetingController {

	IGreetingService service;

	@GetMapping("/greeting")
	public ResponseEntity<String> greeting() {
		return ResponseEntity.ok(service.callGreeting());
	}
}

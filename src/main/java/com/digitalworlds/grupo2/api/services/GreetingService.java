package com.digitalworlds.grupo2.api.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingService implements IGreetingService {

	@Override
	public String callGreeting() {
		return "Un Saludo desde el grupo 2! :)";
	}

}

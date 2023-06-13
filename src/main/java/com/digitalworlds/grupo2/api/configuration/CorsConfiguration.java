package com.digitalworlds.grupo2.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api2/**").allowedOrigins("http://localhost:5173")
						.allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS").maxAge(3600);
			}
		};
	}
}

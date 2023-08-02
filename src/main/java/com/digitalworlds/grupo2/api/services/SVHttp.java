package com.digitalworlds.grupo2.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class SVHttp implements IHttp {

	@Value("${moviedb.token}")
	private String token;

	@Override
	public String getBody(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		RestTemplate rest = new RestTemplate();
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
		return response.getBody();
	}

}

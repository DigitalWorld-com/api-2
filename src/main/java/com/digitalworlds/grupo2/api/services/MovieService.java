package com.digitalworlds.grupo2.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalworlds.grupo2.api.converters.MovieConverter;
import com.digitalworlds.grupo2.api.dtos.MovieApi;
import com.digitalworlds.grupo2.api.dtos.MovieDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService implements IMovieService {

	private final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZTI1ODMxNTE4OWY0Mjg5YTMyYTUzM2RiOTI1MjRkMiIsInN1YiI6IjY0ODc3OWQxYzAzNDhiMDBhZWQ1ODBhYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wwS2w2txVGPSidtQxDAUnJr453NngYnZ0ODSsyUV31k";

	@Override
	public List<MovieDto> getMoviesByTitle(String movieName) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", TOKEN);
		RestTemplate rest = new RestTemplate();

		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		String url = "https://api.themoviedb.org/3/search/movie?query=" + movieName
				+ "&include_adult=false&language=es-AR&page=1";

		ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);

		String responseBody = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(responseBody);

			JsonNode resultsNode = jsonNode.get("results");
			if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
				List<MovieApi> movieList = objectMapper.convertValue(resultsNode, new TypeReference<List<MovieApi>>() {
				});

				return movieList.stream().map(apiMovie -> MovieConverter.convertMovieApiToMovieDto(apiMovie))
						.collect(Collectors.toList());

			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

}

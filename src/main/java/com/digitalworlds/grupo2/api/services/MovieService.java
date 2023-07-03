package com.digitalworlds.grupo2.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalworlds.grupo2.api.converters.MoviesConverter;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.entities.Movies;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MovieService implements IMovieService {

	@Value("${moviedb.token}")
	String token;

	/**
	 * Busca peliculas por nombre de pelicula
	 * 
	 * @param
	 */
	@Override
	public MovieResponse getMoviesByTitle(String movieName) {
		var url = "https://api.themoviedb.org/3/search/movie?query=" + movieName
				+ "&include_adult=false"
				+ "&language=es-AR"
				+ "&page=1";
		String bodyResponse = this.getBodyResponse(url);
		return getMovieResponse(bodyResponse);
	}

	/**
	 * Busca las proximas peliculas
	 */
	@Override
	public MovieResponse getComingSon() {
		var url = "https://api.themoviedb.org/3/discover/movie?"
				+ "include_adult=false"
				+ "&include_video=false"
				+ "&page=1"
				+ "&sort_by=popularity.desc"
				+ "&primary_release_date.gte=2023-06-14"
				+ "&primary_release_date.lte=2023-06-21"
				+ "&language=es-AR"
				+ "&region=AR";
		String bodyResponse = this.getBodyResponse(url);
		return getMovieResponse(bodyResponse);
	}

	// TODO: Verificar si conviene pasarlo a una clase aparte
	private MovieResponse getMovieResponse(String bodyResponse) {
		ObjectMapper oMapper = new ObjectMapper();
		try {
			Movies movies = oMapper.readValue(bodyResponse, Movies.class);
			ModelMapper mMapper = new ModelMapper();
			mMapper.addConverter(new MoviesConverter());
			return mMapper.map(movies, MovieResponse.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	// TODO: Agregar excepcion
	// TODO: Verificar si conviene pasarlo a una clase aparte
	private String getBodyResponse(String requestURL) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		RestTemplate rest = new RestTemplate();
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = rest.exchange(requestURL, HttpMethod.GET, httpEntity, String.class);
		return response.getBody();
	}

}

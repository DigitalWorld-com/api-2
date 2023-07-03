package com.digitalworlds.grupo2.api.services;

import org.springframework.stereotype.Service;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.mappers.MovieMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService implements IMovieService {

	IHttpService http;

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
		return getMoviesByUrl(url);
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
		return getMoviesByUrl(url);
	}

	private MovieResponse getMoviesByUrl(String url) {
		String bodyResponse = http.getBody(url);
		return MovieMapper.getMovieResponse(bodyResponse);
	}


}

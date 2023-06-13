package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.MovieApi;
import com.digitalworlds.grupo2.api.dtos.MovieDto;

public class MovieConverter {
	private static final String URL_BASE = "https://image.tmdb.org/t/p/original";

	public static MovieDto convertMovieApiToMovieDto(MovieApi api) {

		return MovieDto.builder().title(api.getTitle()).description(api.getOverview())
				.imageURL(URL_BASE + api.getPoster_path()).build();
	}
}

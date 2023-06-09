package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.MovieDto;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.models.Movies;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoviesConverter implements Converter<Movies, MovieResponse> {

	private static final String URL_BASE = "https://image.tmdb.org/t/p/original";

	@Override
	public MovieResponse convert(MappingContext<Movies, MovieResponse> context) {
		Movies source = context.getSource();
		MovieResponse destination = Objects.requireNonNullElseGet(context.getDestination(), MovieResponse::new);

		List<MovieDto> listMovies = new ArrayList<>();

		source.getResults().forEach(movieApi -> {
			var movieDto = MovieDto.builder()
				.title(movieApi.getTitle())
				.description(movieApi.getOverview())
				.imageURL(URL_BASE + movieApi.getPoster_path()).build();
			
			listMovies.add(movieDto);
		});

		destination.setMovies(listMovies);
		return destination;
	}

}

package com.digitalworlds.grupo2.api.mappers;

import org.modelmapper.ModelMapper;

import com.digitalworlds.grupo2.api.converters.MoviesConverter;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.entities.Movies;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieMapper {

	public static MovieResponse getMovieResponse(String bodyResponse) {
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

}

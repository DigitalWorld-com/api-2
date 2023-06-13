package com.digitalworlds.grupo2.api.services;

import java.util.List;

import com.digitalworlds.grupo2.api.dtos.MovieDto;

public interface IMovieService {

	public List<MovieDto> getMoviesByTitle(String movieName);
}

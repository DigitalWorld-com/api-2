package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieDto;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;

import java.util.List;


public interface IMovieService {

    public List<MovieDto> getMoviesByTitle(String movieName);

    public MovieResponse getComingSon();

}

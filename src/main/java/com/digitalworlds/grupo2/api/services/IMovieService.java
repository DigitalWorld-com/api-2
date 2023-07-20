package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;


public interface IMovieService {

    public MovieResponse getMoviesByTitle(String movieName);

    public MovieResponse getComingSoon();

}

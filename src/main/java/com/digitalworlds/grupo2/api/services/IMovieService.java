package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;

public interface IMovieService {

    MovieResponse getMoviesByTitle(String movieName);

    MovieResponse getComingSoon(LocalDate from, LocalDate to, String region, String genres) throws JsonProcessingException;

    MovieResponse getComingSoon(String region);

}

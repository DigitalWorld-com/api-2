package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;

import java.time.LocalDate;

public interface IMovieService {

    MovieResponse getMoviesByTitle(String movieName);

    MovieResponse getComingSoon(String region);

    MovieResponse getComingSoon(LocalDate from, LocalDate to, String region, Integer[] genres);
}

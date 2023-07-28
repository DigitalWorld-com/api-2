package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieDto;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.entities.EMovie;
import com.digitalworlds.grupo2.api.mappers.MovieMapper;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
public abstract class MovieService {

    IHttpService http;
    RMovie rMovie;

    protected MovieResponse getMoviesByUrl(String url) {
        String bodyResponse = http.getBody(url);
        MovieResponse movieResponse = MovieMapper.getMovieResponse(bodyResponse);
        //this.registerMovie(movieResponse);
        return movieResponse;
    }

    protected void registerMovie(MovieResponse movieResponse) {
        List<EMovie> eMovies = new ArrayList<>();
        movieResponse
                .getMovies()
                .forEach(
                        movieDto -> {
                            //Si no existe en la BD, la registro
                            if (rMovie.findByTitle(movieDto.getTitle()).isEmpty()) {
                                eMovies.add(this.registerMovie(movieDto));
                            }
                        });
        rMovie.saveAll(eMovies);
    }

    protected EMovie registerMovie(MovieDto movieDto) {
        String shortDesc = movieDto.getDescription().length() < 255 ?
                movieDto.getDescription() : movieDto.getDescription().substring(0, 255);
        EMovie eMovie = EMovie.builder()
                .title(movieDto.getTitle())
                .description(shortDesc)
                .image(movieDto.getImageURL())
                .insertDate(LocalDateTime.now())
                .build();
        return eMovie;
    }

}

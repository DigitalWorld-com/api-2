package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.entities.EMovie;
import com.digitalworlds.grupo2.api.mappers.MovieMapper;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService implements IMovieService {

    IHttpService http;
    RMovie rMovie;

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
    public MovieResponse getComingSoon() {
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
        MovieResponse movieResponse = MovieMapper.getMovieResponse(bodyResponse);
        this.registerMovie(movieResponse);
        return movieResponse;
    }

    private void registerMovie(MovieResponse movieResponse) {
        List<EMovie> eMovies = new ArrayList<>();
        movieResponse.getMovies().forEach(
                movieDto -> {
                    if (Boolean.FALSE.equals(this.isMovieRegistered(movieDto.getTitle()))) {
                        String shortDesc = movieDto.getDescription().length() < 255 ?
                                movieDto.getDescription() : movieDto.getDescription().substring(0, 255);
                        EMovie eMovie = EMovie.builder()
                                .title(movieDto.getTitle())
                                .description(shortDesc)
                                .image(movieDto.getImageURL())
                                .insertDate(LocalDateTime.now())
                                .build();
                        eMovies.add(eMovie);
                    }
                }
        );
        rMovie.saveAll(eMovies);
    }

    private boolean isMovieRegistered(String title) {
        return !rMovie.findByTitle(title).isEmpty();
    }

}

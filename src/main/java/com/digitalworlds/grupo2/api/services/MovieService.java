package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.dtos.MovieDto;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.entities.EMovie;
import com.digitalworlds.grupo2.api.mappers.MovieMapper;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService implements IMovieService {

    IHttpService http;
    RMovie rMovie;
    SVConfig svConfig;

    /**
     * Busca peliculas por nombre de pelicula
     *
     * @param
     */
    @Override
    public MovieResponse getMoviesByTitle(String movieName) {
        var url = "https://api.themoviedb.org/3/search/movie?query=" + movieName
                + "&include_adult=false"
                + "&language=es"
                + "&page=1";
        return getMoviesByUrl(url);
    }

    /**
     * Busca las proximas peliculas
     */
    @Override
    public MovieResponse getComingSoon(String region) {
        DTOConfigComing dtoConfigComing = svConfig.getConfigComing(region);
        LocalDate from = LocalDate.now().minusDays(dtoConfigComing.getDays_before());
        LocalDate to = LocalDate.now().plusDays(dtoConfigComing.getDays_after());

        return this.getComingSoon(from, to, region, dtoConfigComing.getSelected_genres());
    }

    @Override
    public MovieResponse getComingSoon(LocalDate from, LocalDate to, String region, Integer[] genres) {
        log.info("-----------------------");
        log.info("from: " + from);
        log.info("to: " + to);
        log.info("region: " + region);
        log.info("genres: " + genres);
        log.info("-----------------------");
        var url = "https://api.themoviedb.org/3/discover/movie?"
                + "include_adult=false"
                + "&include_video=false"
                + "&page=1"
                + "&sort_by=popularity.desc"
                + "&primary_release_date.gte=" + from.format(DateTimeFormatter.ISO_DATE)
                + "&primary_release_date.lte=" + to.format(DateTimeFormatter.ISO_DATE)
                + "&language=es"
                + "&region=" + region;
        url = this.addFilterGenres(url, genres);
        return getMoviesByUrl(url);
    }

    private String addFilterGenres(String url, Integer[] genres) {
        if (null != genres) {
            List<DTOGenre> listDTOGenreAll = Arrays.asList(svConfig.getAllGenres());
            List<Integer> listSelectedGenres = Arrays.asList(genres);
            Collections.sort(listSelectedGenres);
            boolean allGenres = listDTOGenreAll.stream()
                    .allMatch(dtoGenre -> listSelectedGenres.contains(dtoGenre.getId()));
            if (!allGenres) {
                url += "&genres=";
                for (int idGenre : listSelectedGenres) {
                    url += "|" + idGenre;
                }
            }
        }
        return url;
    }

    private MovieResponse getMoviesByUrl(String url) {
        String bodyResponse = http.getBody(url);
        MovieResponse movieResponse = MovieMapper.getMovieResponse(bodyResponse);
        this.registerMovie(movieResponse);
        return movieResponse;
    }

    private void registerMovie(MovieResponse movieResponse) {
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

    private EMovie registerMovie(MovieDto movieDto) {
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

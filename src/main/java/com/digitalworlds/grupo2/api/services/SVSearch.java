package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SVSearch extends MovieService {

    IInfo iInfo;

    public SVSearch(IHttpService http, RMovie rMovie, IInfo iInfo) {
        super(http, rMovie);
        this.iInfo = iInfo;
    }

    /**
     * Busca peliculas por nombre de pelicula
     *
     * @param
     */
    public MovieResponse getMoviesByTitle(String movieName) {
        var url = "https://api.themoviedb.org/3/search/movie?query=" + movieName
                + "&include_adult=false"
                + "&language=es"
                + "&page=1";

        //filter by GENRES

        return getMoviesByUrl(url);
    }

}

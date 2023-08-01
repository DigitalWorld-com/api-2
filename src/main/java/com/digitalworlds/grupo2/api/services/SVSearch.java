package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SVSearch extends MovieService {

    private IInfo iInfo;

    public SVSearch(IHttpService http, RMovie rMovie, IInfo iInfo) {
        super(http, rMovie);
        this.iInfo = iInfo;
    }

    /**
     * Busca peliculas por nombre de pelicula
     *
     * @param
     */
    public List<DTOMovie> getMoviesByTitle(String movieName) {
        var url = "https://api.themoviedb.org/3/search/movie?query=" + movieName
                + "&include_adult=false"
                + "&language=es"
                + "&page=1";

        List<DTOMovie> listDtoMovie = this.getMoviesByUrl(url);

        //TODO filter by GENRES
        // add dtoMovie & Movies: int [] genres
        // genres.contains(arg_selectedGenres)

        return listDtoMovie;
    }

}

package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SVComing extends MovieService {
    IConfigComing iConfigComing;

    public SVComing(IHttpService http, RMovie rMovie, IInfo iInfo, IConfigComing iConfigComing) {
        super(http, rMovie);
        this.iConfigComing = iConfigComing;
    }

    /**
     * Busca las proximas peliculas
     */
    public MovieResponse getComingSoon(String region) {
        DTOConfigComing dtoConfigComing = iConfigComing.getConfigComing(region);
        LocalDate from = LocalDate.now().minusDays(dtoConfigComing.getDays_before());
        LocalDate to = LocalDate.now().plusDays(dtoConfigComing.getDays_after());

        return this.getComingSoon(from, to, region, dtoConfigComing.getSelected_genres());
    }

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
            List<DTOGenre> listDTOGenreAll = Arrays.asList(iConfigComing.getIInfo().getAllGenres());
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

}

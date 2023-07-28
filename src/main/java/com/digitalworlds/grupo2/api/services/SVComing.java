package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import com.digitalworlds.grupo2.api.util.StringOR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class SVComing extends MovieService {
    IConfigComing iConfigComing;

    public SVComing(IHttpService http, RMovie rMovie, IConfigComing iConfigComing) {
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
        String stringORSelectedGenres = StringOR.convert(dtoConfigComing.getSelected_genres());

        return this.getComingSoon(from, to, region, stringORSelectedGenres);
    }

    private MovieResponse getComingSoon(LocalDate from, LocalDate to, String region, String genres) {
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
        if (genres != null) {
            url += "&genres=" + genres;
        }
        return getMoviesByUrl(url);
    }

}

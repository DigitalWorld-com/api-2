package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.converters.CVTMovie;
import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.entities.EMovie;
import com.digitalworlds.grupo2.api.models.Movie;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class MovieService {

    private IHttpService http;
    private RMovie rMovie;
    private ObjectMapper oMapper = new ObjectMapper();
    private ModelMapper mMapper = new ModelMapper();
    private TypeReference<List<Movie>> typeListMovie = new TypeReference<>() {
    };

    public MovieService(IHttpService http, RMovie rMovie) {
        this.http = http;
        this.rMovie = rMovie;
    }

    protected List<DTOMovie> getMoviesByUrl(String url) {
        try {
            String bodyResponse = http.getBody(url);

            String strArrayMovies = oMapper.readTree(bodyResponse).get("results").toString();
            List<Movie> listMovie = oMapper.readValue(strArrayMovies, typeListMovie);

            mMapper.addConverter(new CVTMovie());
            List<DTOMovie> listDtoMovie = listMovie.stream()
                    .map(user -> mMapper.map(user, DTOMovie.class))
                    .collect(Collectors.toList());

            return listDtoMovie;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void registerMovie(List<DTOMovie> listDtoMovie) {
        List<EMovie> eMovies = new ArrayList<>();
        listDtoMovie.forEach(
                movieDto -> {
                    //Si no existe en la BD, la registro
                    if (rMovie.findByTitle(movieDto.getTitle()).isEmpty()) {
                        eMovies.add(this.registerMovie(movieDto));
                    }
                });
        rMovie.saveAll(eMovies);
    }

    private EMovie registerMovie(DTOMovie movieDto) {
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

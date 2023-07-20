package com.digitalworlds.grupo2.api.controllers;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.services.MovieService;
import com.digitalworlds.grupo2.api.services.SVCountry;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api2/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

    MovieService service;
    SVCountry svCountry;

    @GetMapping("/movie/{movieName}")
    @ApiOperation(value = "Busca películas por Título")
    public ResponseEntity<MovieResponse> getMoviesByName(@PathVariable String movieName) {
        log.info("Buscando pelicula: " + movieName);
        var response = service.getMoviesByTitle(movieName);
        log.info("Peliculas encontradas.");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = {"/comingsoon", "/comingsoon/{from}/{to}/{region}"})
    @ApiOperation(value = "Obtiene los próximos estrenos de Cine")
    public ResponseEntity<MovieResponse> getComingSoonMovies(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PathVariable String region) {
        log.info("<<<<< ---- /comingsoon ---- >>>>>");
        log.info("from: " + from);
        log.info("to: " + to);
        log.info("region: " + region);
        log.info("-----------------------");
        ResponseEntity<MovieResponse> response = ResponseEntity.ok(service.getComingSoon());
        log.info("<//// ---- /comingsoon ---- ////>[OK]");
        return response;
    }

    @GetMapping(value = {"/countries"})
    @ApiOperation(value = "Obtiene las regiones (ISO 3166-1) y sus respectivas banderas")
    public ResponseEntity<DTOCountry[]> getAllCountries() throws JsonProcessingException {
        log.info("<<<<< ---- /countries ---- >>>>>");
        ResponseEntity<DTOCountry[]> response = ResponseEntity.ok(svCountry.getAllCountries());
        log.info("<//// ---- /countries ---- ////>[OK]");
        return response;
    }

}

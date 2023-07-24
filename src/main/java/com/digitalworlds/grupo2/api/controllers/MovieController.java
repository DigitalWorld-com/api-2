package com.digitalworlds.grupo2.api.controllers;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.services.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api2/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

    MovieService service;

    @GetMapping("/movie/{movieName}")
    @ApiOperation("Busca películas por Título")
    public ResponseEntity<MovieResponse> getMoviesByName(@PathVariable String movieName) {
        log.info("Buscando pelicula: " + movieName);
        var response = service.getMoviesByTitle(movieName);
        log.info("Peliculas encontradas.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comingsoon")
    @ApiOperation("Obtiene los próximos estrenos de Cine")
    public ResponseEntity<MovieResponse> getComingSoonMovies(
            @ApiParam(value = "Ej. 2023-07-15", example = "2023-07-15") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @ApiParam(value = "Ej. 2023-07-31", example = "2023-07-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @ApiParam(value = "Ej. AR, ES, US, etc.", example = "AR") @RequestParam String region,
            @ApiParam(value = "Ej. |28|53", example = "|28|53") @RequestParam(required = false) String genres) {
        log.info("<<<<< ---- /comingsoon ---- >>>>>");
        ResponseEntity<MovieResponse> response = ResponseEntity.ok(service.getComingSoon(from, to, region, genres));
        log.info("<//// ---- /comingsoon ---- ////>[OK]");
        return response;
    }

    @GetMapping("/comingsoon/{region}")
    @ApiOperation("Obtiene los próximos estrenos de Cine")
    public ResponseEntity<MovieResponse> getComingSoonMovies(
            @ApiParam(value = "Ej. AR, ES, US, etc.", example = "AR") @PathVariable String region) {
        log.info("<<<<< ---- /comingsoon ---- >>>>>");
        ResponseEntity<MovieResponse> response = ResponseEntity.ok(service.getComingSoon(region));
        log.info("<//// ---- /comingsoon ---- ////>[OK]");
        return response;
    }

}

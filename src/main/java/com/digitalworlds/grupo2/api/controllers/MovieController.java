package com.digitalworlds.grupo2.api.controllers;

import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.services.SVComing;
import com.digitalworlds.grupo2.api.services.SVSearch;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api2/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

    SVSearch svSearch;
    SVComing svComing;

    @GetMapping("/movie/{movieName}")
    @ApiOperation("Busca películas por Título")
    public ResponseEntity<List<DTOMovie>> getMoviesByName(@PathVariable String movieName) {
        log.info("Buscando pelicula: " + movieName);
        var response = svSearch.getMoviesByTitle(movieName);
        log.info("Peliculas encontradas.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/comingsoon/{region}")
    @ApiOperation("Obtiene los próximos estrenos de Cine (requiere configurar el país previamente)")
    public ResponseEntity<List<DTOMovie>> getComingSoonMovies(
            @ApiParam(value = "Ej. AR, ES, US, etc.", example = "AR") @PathVariable String region) {
        log.info("<<<<< ---- [GET]/comingsoon ---- >>>>>");
        var response = ResponseEntity.ok(svComing.getComingSoon(region));
        log.info("<//// ---- [GET]/comingsoon ---- ////>[OK]");
        return response;
    }

}

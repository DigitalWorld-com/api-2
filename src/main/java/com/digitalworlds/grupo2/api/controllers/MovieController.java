package com.digitalworlds.grupo2.api.controllers;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api2/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

	MovieService service;

	@GetMapping("/movie/{movieName}")
	public ResponseEntity<MovieResponse> getMoviesByName(@PathVariable String movieName) {
		log.info("Buscando pelicula: " + movieName);
		var response = service.getMoviesByTitle(movieName);
		log.info("Peliculas encontradas.");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/coming")
	public ResponseEntity<MovieResponse> getComingSoonMovies() {
		log.info("Buscando proximas peliculas.");
		var response = ResponseEntity.ok(service.getComingSon());
		log.info("Proximas peliculas encontradas.");
		return response;
	}

}

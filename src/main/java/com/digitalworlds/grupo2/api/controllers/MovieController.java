package com.digitalworlds.grupo2.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.services.MovieService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api2/movie")
@AllArgsConstructor
@Slf4j
public class MovieController {

	MovieService service;

	@GetMapping("/{movieName}")
	public ResponseEntity<MovieResponse> getMoviesByName(@PathVariable String movieName) {
		log.info("Buscando pelicula: " + movieName);

		var response = MovieResponse.builder().movies(service.getMoviesByTitle(movieName)).build();

		log.info("Peliculas encontradas.");
		return ResponseEntity.ok(response);
	}
}

package com.digitalworlds.grupo2.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.services.SVSearch;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SVSearch svSearch;

	@InjectMocks
	private MovieController controller;

	private List<DTOMovie> response = Arrays.asList(
					DTOMovie.builder()
					.title("pelicula")
					.imageURL("url")
					.description("aca hay una peli piola")
					.build()
					);

	@BeforeEach
	public void setUp() throws URISyntaxException {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Cuando busco pelicula por titulo me devuelve una pelicula.")
	void testGetMoviesByName() throws Exception {
		when(svSearch.getMoviesByTitle(any())).thenReturn(response);
		var jsonResponse = "{\"movies\": [{\"title\": \"pelicula\",\"description\": \"aca hay una peli piola\",\"imageURL\": \"url\"}]}";
		mvc.perform(get("/api2/movies/movie/pelicula")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content().json(jsonResponse))
			      ;
		
	}

//	@Test
//	@DisplayName("Cuando busco las proximas peliculas me devuelve la pelicula que saldra.")
//	void testGetComingSoonMovies()   throws Exception {
//		when(service.getComingSoon()).thenReturn(response);
//		var jsonResponse = "{\"movies\": [{\"title\": \"pelicula\",\"description\": \"aca hay una peli piola\",\"imageURL\": \"url\"}]}";
//		mvc.perform(get("/api2/movies/coming")
//			      .contentType(MediaType.APPLICATION_JSON))
//			      .andExpect(status().isOk())
//			      .andExpect(content().json(jsonResponse))
//			      ;
//	}


}

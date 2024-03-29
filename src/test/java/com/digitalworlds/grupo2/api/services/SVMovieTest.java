package com.digitalworlds.grupo2.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EMovie;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import com.digitalworlds.grupo2.api.repositories.RSearch;

@SuppressWarnings("unchecked")
public class SVMovieTest {

	private static final String mockResponse = "{\"results\": [{\"title\": \"Movie 1\", \"overview\": \"Description 1\", \"poster_path\": \"/image1.jpg\"}]}";

	private static final DTOMovie movieDto = DTOMovie.builder().title("Movie 1").description("Description 1")
			.imageURL("https://image.tmdb.org/t/p/original/image1.jpg").build();

	private static final String LONG_DESC = "En un mundo postapocalíptico, un grupo de supervivientes lucha por sobrevivir en un paisaje devastado. Con recursos limitados y en constante peligro, deberán enfrentarse a desafíos mortales mientras buscan un refugio seguro. Una historia de valentía, esperanza y la lucha por la supervivencia en medio de la adversidad. ¿Podrán encontrar la salvación o sucumbirán a las amenazas que acechan en cada esquina? Un emocionante viaje lleno de acción, suspenso y giros inesperados.";

	@Mock
	private SVHttp svHttp;

	@Mock
	private RMovie rMovie;

    @Mock
	private RSearch rSearch;

    @Mock
    private IInfo iInfo;

	@Mock
	SVConfigComing config;

	@InjectMocks
	private SVSearch svSearch;

	@InjectMocks
	private SVComing svComing;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("El Servicio devuelve una pelicula buscada por nombre.")
	public void testGetMoviesByTitle() {
		when(svHttp.getBody(any())).thenReturn(mockResponse);
		when(rMovie.saveAll(any())).thenReturn(new ArrayList<>());
		
		String movieName = "Test Movie";
		List<DTOMovie> result = svSearch.getMoviesByTitle(movieName);

		assertEquals(movieDto, result.get(0));
	}

	@Test
	@DisplayName("El servicio devuelve las peliculas que vienen en camino.")
	public void testGetComingSoonAllNews() {
		var region = "AR";
		when(config.getConfigComing(region))
				.thenReturn(DTOConfigComing.builder().region(region).days_before(7).days_after(7).build());
		when(svHttp.getBody(anyString())).thenReturn(mockResponse);
		when(rMovie.saveAll(any())).thenReturn(new ArrayList<>());

		List<DTOMovie> result = svComing.getComingSoon("AR");

		assertEquals(movieDto, result.get(0));
	}

	@Test
	@DisplayName("El servicio si encuentra una pelicula no lo guarda en la base.")
	public void testGetComingSoonWithFounds() {
		var region = "AR";
		when(config.getConfigComing(region)).thenReturn(DTOConfigComing.builder()
                    .region(region)
                    .days_before(7)
                    .days_after(7)
                    .build());
		when(svHttp.getBody(anyString())).thenReturn(mockResponse);
		when(rMovie.saveAll(any())).thenReturn(new ArrayList<>());
		when(rMovie.findByTitle(any())).thenReturn(Arrays.asList(EMovie.builder().build()));
		
		// Capturamos lo que le pasamos al saveAll
		
		ArgumentCaptor<List<EMovie>> captor = ArgumentCaptor.forClass(List.class);
		when(rMovie.saveAll(captor.capture())).thenReturn(new ArrayList<>());

		List<DTOMovie> result = svComing.getComingSoon("AR");
		
		// Verificamos lo que se capturo
		List<EMovie> savedMovies = captor.getValue();
		assertEquals(0, savedMovies.size());
		

		assertEquals(movieDto, result.get(0));
	}

	@Test
	@DisplayName("El servicio que no encuentra una pelicula lo guarda en la base con la descripcion enviada.")
	public void testGetComingSoonWithNotFoundsWithShortDescription() {
		var region = "AR";
		when(config.getConfigComing(region)).thenReturn(DTOConfigComing.builder()
                    .region(region)
                    .days_before(7)
                    .days_after(7)
                    .build());
		
		when(svHttp.getBody(anyString())).thenReturn(mockResponse);

		// Capturamos lo que le pasamos al saveAll
		ArgumentCaptor<List<EMovie>> captor = ArgumentCaptor.forClass(List.class);
		when(rMovie.saveAll(captor.capture())).thenReturn(new ArrayList<>());

		when(rMovie.findByTitle(any())).thenReturn(new ArrayList<>());

		svComing.getComingSoon("AR");

		// Verificamos lo que se capturo
		List<EMovie> savedMovies = captor.getValue();
		assertEquals(1, savedMovies.size());
		assertEquals("Description 1", savedMovies.get(0).getDescription());
	}

	@Test
	@DisplayName("El servicio que no encuentra una película lo guarda en la base con la descripción cortada.")
	public void testGetComingSoonWithFoundsWithLongDescription() {
		var region = "AR";
		when(config.getConfigComing(region)).thenReturn(DTOConfigComing.builder()
                    .region(region)
                    .days_before(7)
                    .days_after(7)
                    .build());

		String mockResponseLongDescription = "{\"results\": [{\"title\": \"Movie 1\", \"overview\": \"" + LONG_DESC
				+ "\", \"poster_path\": \"/image1.jpg\"}]}";
		when(svHttp.getBody(anyString())).thenReturn(mockResponseLongDescription);

		// Capturamos lo que le pasamos al saveAll
		ArgumentCaptor<List<EMovie>> captor = ArgumentCaptor.forClass(List.class);
		when(rMovie.saveAll(captor.capture())).thenReturn(new ArrayList<>());

		when(rMovie.findByTitle(any())).thenReturn(new ArrayList<>());

		svComing.getComingSoon("AR");

		// Verificamos lo que se capturo
		List<EMovie> savedMovies = captor.getValue();
		assertEquals(1, savedMovies.size());
		assertEquals(LONG_DESC.substring(0, 255), savedMovies.get(0).getDescription());
	}

}

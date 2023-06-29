package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.converters.MovieConverter;
import com.digitalworlds.grupo2.api.converters.MoviesConverter;
import com.digitalworlds.grupo2.api.dtos.MovieDto;
import com.digitalworlds.grupo2.api.dtos.MovieResponse;
import com.digitalworlds.grupo2.api.entities.Movie;
import com.digitalworlds.grupo2.api.entities.Movies;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MovieService implements IMovieService {

    private final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZTI1ODMxNTE4OWY0Mjg5YTMyYTUzM2RiOTI1MjRkMiIsInN1YiI6IjY0ODc3OWQxYzAzNDhiMDBhZWQ1ODBhYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wwS2w2txVGPSidtQxDAUnJr453NngYnZ0ODSsyUV31k";

    @Override
    public List<MovieDto> getMoviesByTitle(String movieName) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        RestTemplate rest = new RestTemplate();

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String url = "https://api.themoviedb.org/3/search/movie?query=" + movieName
                + "&include_adult=false&language=es-AR&page=1";

        ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            JsonNode resultsNode = jsonNode.get("results");
            if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                List<Movie> movieList = objectMapper.convertValue(resultsNode, new TypeReference<List<Movie>>() {
                });

                return movieList.stream().map(apiMovie -> MovieConverter.convertMovieApiToMovieDto(apiMovie))
                        .collect(Collectors.toList());

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MovieResponse getComingSon() {
        String bodyResponse = this.getBodyResponse("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&page=1&sort_by=popularity.desc&primary_release_date.gte=2023-06-14&primary_release_date.lte=2023-06-21&language=es-AR&region=AR");

        ObjectMapper oMapper = new ObjectMapper();
        try {
            Movies movies = oMapper.readValue(bodyResponse, Movies.class);
            ModelMapper mMapper = new ModelMapper();
            mMapper.addConverter(new MoviesConverter());
            return mMapper.map(movies, MovieResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getBodyResponse(String requestURL) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        RestTemplate rest = new RestTemplate();

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = rest.exchange(requestURL, HttpMethod.GET, httpEntity, String.class);
        return response.getBody();
    }

}

package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.models.Genre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SVGenre implements IGenre {

    private IHttpService http;
    private ObjectMapper oMapper;
    private ModelMapper mMapper;

    @Override
    public DTOGenre[] getAllGenres() {
        try {
            String bodyResponse = http.getBody("https://api.themoviedb.org/3/genre/movie/list");

            String strArrayGenres = oMapper.readTree(bodyResponse).get("genres").toString();
            Genre[] arrayGenres = oMapper.readValue(strArrayGenres, Genre[].class);
            DTOGenre[] arrayDtoGenre = mMapper.map(arrayGenres, DTOGenre[].class);

            return arrayDtoGenre;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}

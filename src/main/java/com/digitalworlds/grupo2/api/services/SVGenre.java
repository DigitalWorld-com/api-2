package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.models.Genre;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SVGenre implements IGenre {

    private IHttpService http;

    @Override
    public DTOGenre[] getAllGenres() {
        try {
            String bodyResponse = http.getBody("https://api.themoviedb.org/3/genre/movie/list");

            String strArrayGenres = UtilCvt.OBJECT_MAPPER.readTree(bodyResponse).get("genres").toString();
            Genre[] arrayGenres = UtilCvt.OBJECT_MAPPER.readValue(strArrayGenres, Genre[].class);
            DTOGenre[] arrayDtoGenre = UtilCvt.MODEL_MAPPER.map(arrayGenres, DTOGenre[].class);

            return arrayDtoGenre;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}

package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.models.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@Service
@AllArgsConstructor
@Slf4j
public class SVCountry implements ICountry {
    private IHttpService http;
    private ObjectMapper oMapper;
    private ModelMapper mMapper;
    private ResourceLoader resourceLoader;

    @Override
    public DTOCountry[] getAllCountries() throws JsonProcessingException {
        String bodyResponse = http.getBody("https://api.themoviedb.org/3/configuration/countries");

        Country[] arrayCountry = oMapper.readValue(bodyResponse, Country[].class);
        DTOCountry[] arrayDtoCountry = mMapper.map(arrayCountry, DTOCountry[].class);

        Arrays.asList(arrayDtoCountry).forEach(
                dtoCountry -> this.setCountryFlag(dtoCountry));

        return arrayDtoCountry;
    }

    private void setCountryFlag(DTOCountry dtoCountry) {
        try {
            byte[] imgAsBytes = Files.readAllBytes(resourceLoader.getResource(
                    "classpath:country-flags/" + dtoCountry.getIso_3166_1() + ".png").getFile().toPath());
            dtoCountry.setCountryFlag(imgAsBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

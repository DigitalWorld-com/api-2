package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.models.Country;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SVCountry implements ICountry {

    private IHttpService http;
    private ResourceLoader resourceLoader;

    @Override
    public DTOCountry[] getAllCountries() {
        try {
            String bodyResponse = http.getBody("https://api.themoviedb.org/3/configuration/countries");

            Country[] arrayCountry = UtilCvt.OBJECT_MAPPER.readValue(bodyResponse, Country[].class);
            DTOCountry[] arrayDtoCountry = UtilCvt.MODEL_MAPPER.map(arrayCountry, DTOCountry[].class);

            //Agrego icono de bandera
            List<DTOCountry> listDtoCountry = Arrays.asList(arrayDtoCountry);
            listDtoCountry.forEach(dtoCountry -> this.setCountryFlag(dtoCountry));
            //Filtro aquellas sin bandera
            listDtoCountry = listDtoCountry.stream()
                    .filter(dtoCountry -> null != dtoCountry.getCountryFlag())
                    .collect(Collectors.toList());

            arrayDtoCountry = new DTOCountry[listDtoCountry.size()];
            return listDtoCountry.toArray(arrayDtoCountry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
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

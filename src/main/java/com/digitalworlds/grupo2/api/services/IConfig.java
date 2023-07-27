package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConfig {

    DTOCountry[] getAllCountries() throws JsonProcessingException;

    DTOGenre[] getAllGenres() throws JsonProcessingException;

    DTOConfigComing getConfigComing(String region);

    DTOConfigComing postConfigComing(DTOConfigComing dtoConfigComing);

    DTOConfigComing putConfigComing(DTOConfigComing dtoConfigComing);

    DTOConfigComing deleteConfigComing(String region);

}

package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConfig {

    DTOCountry[] getAllCountries() throws JsonProcessingException;

    DTOGenre[] getAllGenres() throws JsonProcessingException;

}

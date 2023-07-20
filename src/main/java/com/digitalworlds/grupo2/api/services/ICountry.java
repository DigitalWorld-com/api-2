package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ICountry {

    DTOCountry[] getAllCountries() throws JsonProcessingException;

}

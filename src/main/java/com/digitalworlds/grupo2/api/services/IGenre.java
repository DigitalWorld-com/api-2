package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IGenre {

    DTOGenre[] getAllGenres() throws JsonProcessingException;

}

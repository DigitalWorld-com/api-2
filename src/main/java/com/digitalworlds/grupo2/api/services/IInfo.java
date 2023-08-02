package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;

public interface IInfo {

    DTOCountry[] getAllCountries();

    DTOGenre[] getAllGenres();

    void verifyCountry(String region);

    void verifyGenre(Integer[] selectedGenres);

    boolean isAllGenresSelected(Integer[] selectedGenres);

}

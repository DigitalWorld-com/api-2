package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class SVInfo implements IInfo {
    ICountry iCountry;
    IGenre iGenre;

    @Override
    public DTOCountry[] getAllCountries() {
        return iCountry.getAllCountries();
    }

    @Override
    public DTOGenre[] getAllGenres() {
        return iGenre.getAllGenres();
    }

    @Override
    public void verifyCountry(String region) {
        Assert.isTrue(
                Arrays.stream(iCountry.getAllCountries()).anyMatch(dtoCountry -> dtoCountry.getIso_3166_1().equals(region)),
                "No existe la region solicitada...");
    }

    @Override
    public void verifyGenre(Integer[] selectedGenres) {
        if (selectedGenres != null && selectedGenres.length > 0) {
            List<Integer> listSelectedGenres = Arrays.asList(selectedGenres);
            Assert.isTrue(
                    Arrays.stream(iGenre.getAllGenres()).anyMatch(dtoGenre -> listSelectedGenres.contains(selectedGenres)),
                    "Hay géneros inválidos...");
        }
    }
}

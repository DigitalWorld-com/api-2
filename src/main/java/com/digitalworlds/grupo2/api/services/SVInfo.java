package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@AllArgsConstructor
@Slf4j
public class SVInfo implements IInfo {
    private ICountry iCountry;
    private IGenre iGenre;

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
        Assert.isTrue(Stream.of(iCountry.getAllCountries())
                        .anyMatch(dtoCountry -> dtoCountry.getIso_3166_1().equals(region)),
                "No existe la region solicitada...");
    }

    @Override
    public void verifyGenre(Integer[] selectedGenres) {
        if (selectedGenres != null && selectedGenres.length > 0) {
            List<Integer> listAllGenres = Stream.of(iGenre.getAllGenres())
                    .map(DTOGenre::getId)
                    .collect(Collectors.toList());

            List<Integer> listSelectedGenres = List.of(selectedGenres);

            Assert.isTrue(listAllGenres.containsAll(listSelectedGenres),
                    "Hay géneros inválidos...");
        }
    }

    @Override
    public boolean isAllGenresSelected(Integer[] selectedGenres) {
        if (selectedGenres != null && selectedGenres.length > 0) {
            return selectedGenres.length == iGenre.getAllGenres().length;
        }
        return true;
    }

}

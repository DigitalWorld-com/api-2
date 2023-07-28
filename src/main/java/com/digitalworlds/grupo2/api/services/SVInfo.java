package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            List<Integer> listSelectedGenres = List.of(selectedGenres);
            List<Integer> listAllGenresIds = new ArrayList<>();
            List<DTOGenre> listDTOGenre = List.of(iGenre.getAllGenres());
            listDTOGenre.stream().forEach(dtoGenre -> listAllGenresIds.add(dtoGenre.getId()));
            Assert.isTrue(
                    listSelectedGenres.stream().allMatch(idGenre -> listAllGenresIds.contains(idGenre)),
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

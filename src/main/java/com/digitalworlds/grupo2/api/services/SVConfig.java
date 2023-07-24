package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.repositories.RConfigComing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SVConfig implements IConfig {

    SVCountry svCountry;
    SVGenre svGenre;
    RConfigComing rConfigComing;

    @Override
    public DTOCountry[] getAllCountries() {
        return svCountry.getAllCountries();
    }

    @Override
    public DTOGenre[] getAllGenres() {
        return svGenre.getAllGenres();
    }

    public EConfigComing getConfigComing(String region) {
        Assert.isTrue(
                Arrays.stream(svCountry.getAllCountries()).anyMatch(dtoCountry -> dtoCountry.getIso_3166_1().equals(region)),
                "No existe la region solicitada...");
        Optional<EConfigComing> optEConfigComing = rConfigComing.findById(region);
        if (optEConfigComing.isPresent()) {
            return optEConfigComing.get();
        } else {
            EConfigComing eDefaultConfigComing = EConfigComing.builder()
                    .region(region)
                    .days_before(7)
                    .days_after(7)
                    .build();
            rConfigComing.save(eDefaultConfigComing);
            return eDefaultConfigComing;
        }
    }

}

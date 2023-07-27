package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.converters.CVTConfigComingToDTO;
import com.digitalworlds.grupo2.api.converters.CVTConfigComingToE;
import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.dtos.DTOCountry;
import com.digitalworlds.grupo2.api.dtos.DTOGenre;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.repositories.RConfigComing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;

    @Override
    public DTOCountry[] getAllCountries() {
        return svCountry.getAllCountries();
    }

    @Override
    public DTOGenre[] getAllGenres() {
        return svGenre.getAllGenres();
    }

    @Override
    public DTOConfigComing getConfigComing(String region) {
        EConfigComing optEConfigComing = this.getEConfigComing(region);

        /*EConfigComing eDefaultConfigComing;
        if (optEConfigComing.isPresent()) {
            eDefaultConfigComing = optEConfigComing.get();
        } else {
            eDefaultConfigComing = EConfigComing.builder()
                    .region(region)
                    .days_before(7)
                    .days_after(7)
                    .build();
            rConfigComing.save(eDefaultConfigComing);
        }*/

        modelMapper.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoConfigComing = modelMapper.map(optEConfigComing, DTOConfigComing.class);

        return dtoConfigComing;
    }

    @Override
    public DTOConfigComing postConfigComing(DTOConfigComing dtoConfigComing) {
        return this.setConfigComing(dtoConfigComing);
    }

    @Override
    public DTOConfigComing putConfigComing(DTOConfigComing dtoConfigComing) {
        //para chequear que exista previamente
        this.getEConfigComing(dtoConfigComing.getRegion());
        return this.setConfigComing(dtoConfigComing);
    }

    @Override
    public DTOConfigComing deleteConfigComing(String region) {
        //chequeo que exista previamente
        EConfigComing eConfigComing = this.getEConfigComing(region);
        rConfigComing.delete(eConfigComing);

        modelMapper.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoBackUp = modelMapper.map(eConfigComing, DTOConfigComing.class);
        return dtoBackUp;
    }

    private DTOConfigComing setConfigComing(DTOConfigComing dtoConfigComing) {
        modelMapper.addConverter(new CVTConfigComingToE());
        EConfigComing eConfigComing = modelMapper.map(dtoConfigComing, EConfigComing.class);
        rConfigComing.save(eConfigComing);

        //dtoAfterSave
        modelMapper.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoAfterSave = modelMapper.map(this.getEConfigComing(dtoConfigComing.getRegion()), DTOConfigComing.class);
        return dtoAfterSave;
    }

    private EConfigComing getEConfigComing(String region) {
        Assert.isTrue(
                Arrays.stream(svCountry.getAllCountries()).anyMatch(dtoCountry -> dtoCountry.getIso_3166_1().equals(region)),
                "No existe la region solicitada...");
        Optional<EConfigComing> optEConfigComing = rConfigComing.findById(region);
        Assert.isTrue(optEConfigComing.isPresent(), "Debe establecer la configuracion de estrenos para este país previamente...");
        return optEConfigComing.get();
    }

}

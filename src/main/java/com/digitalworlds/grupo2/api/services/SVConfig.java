package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.converters.CVTConfigComingToDTO;
import com.digitalworlds.grupo2.api.converters.CVTConfigComingToE;
import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.repositories.RConfigComing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SVConfig implements IConfig {

    IInfo iInfo;
    RConfigComing rConfigComing;
    ModelMapper modelMapper;

    @Override
    public DTOConfigComing getConfigComing(String region) {
        EConfigComing eConfigComing = this.getEConfigComing(region);

        modelMapper.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoConfigComing = modelMapper.map(eConfigComing, DTOConfigComing.class);

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

    @Override
    public IInfo getIInfo() {
        return iInfo;
    }

    private DTOConfigComing setConfigComing(DTOConfigComing dtoConfigComing) {
        modelMapper.addConverter(new CVTConfigComingToE(iInfo));
        EConfigComing eConfigComing = modelMapper.map(dtoConfigComing, EConfigComing.class);
        rConfigComing.save(eConfigComing);

        //dtoAfterSave
        modelMapper.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoAfterSave = modelMapper.map(this.getEConfigComing(dtoConfigComing.getRegion()), DTOConfigComing.class);
        return dtoAfterSave;
    }

    private EConfigComing getEConfigComing(String region) {
        Optional<EConfigComing> optEConfigComing = rConfigComing.findById(region);
        Assert.isTrue(optEConfigComing.isPresent(), "Debe establecer la configuracion de estrenos para este país previamente...");
        return optEConfigComing.get();
    }

}

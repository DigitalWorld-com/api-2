package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.converters.CVTConfigComingToDTO;
import com.digitalworlds.grupo2.api.converters.CVTConfigComingToE;
import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.repositories.RConfigComing;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SVConfigComing implements IConfigComing {

    private IInfo iInfo;
    private RConfigComing rConfigComing;

    @Override
    public DTOConfigComing getConfigComing(String region) {
        EConfigComing eConfigComing = this.getEConfigComing(region);

        UtilCvt.MODEL_MAPPER.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoConfigComing = UtilCvt.MODEL_MAPPER.map(eConfigComing, DTOConfigComing.class);

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

        UtilCvt.MODEL_MAPPER.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoBackUp = UtilCvt.MODEL_MAPPER.map(eConfigComing, DTOConfigComing.class);
        return dtoBackUp;
    }

    private DTOConfigComing setConfigComing(DTOConfigComing dtoConfigComing) {
        UtilCvt.MODEL_MAPPER.addConverter(new CVTConfigComingToE(iInfo));
        EConfigComing eConfigComing = UtilCvt.MODEL_MAPPER.map(dtoConfigComing, EConfigComing.class);
        rConfigComing.save(eConfigComing);

        //dtoAfterSave
        UtilCvt.MODEL_MAPPER.addConverter(new CVTConfigComingToDTO());
        DTOConfigComing dtoAfterSave = UtilCvt.MODEL_MAPPER.map(eConfigComing, DTOConfigComing.class);
        return dtoAfterSave;
    }

    private EConfigComing getEConfigComing(String region) {
        Optional<EConfigComing> optEConfigComing = rConfigComing.findById(region);
        Assert.isTrue(optEConfigComing.isPresent(), "Debe establecer la configuracion de estrenos para este país previamente...");
        return optEConfigComing.get();
    }

}

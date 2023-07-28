package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.services.IInfo;
import com.digitalworlds.grupo2.api.util.StringOR;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class CVTConfigComingToE implements Converter<DTOConfigComing, EConfigComing> {

    IInfo iInfo;

    public CVTConfigComingToE(IInfo iInfo) {
        this.iInfo = iInfo;
    }

    @Override
    public EConfigComing convert(MappingContext<DTOConfigComing, EConfigComing> context) {
        DTOConfigComing source = context.getSource();

        //Verificar que la region y género sean válidos
        iInfo.verifyCountry(source.getRegion());
        iInfo.verifyGenre(source.getSelected_genres());

        //TODO si tiene todos los generos entonces '.selected_genres(null)'
        // ELIMINAR O REDUCIR 'addFilterGenres'

        EConfigComing destination = EConfigComing.builder()
                .region(source.getRegion())
                .days_before(source.getDays_before())
                .days_after(source.getDays_after())
                .selected_genres(StringOR.convert(source.getSelected_genres()))
                .build();

        return destination;
    }

}

package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.util.StringOR;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class CVTConfigComingToE implements Converter<DTOConfigComing, EConfigComing> {

    @Override
    public EConfigComing convert(MappingContext<DTOConfigComing, EConfigComing> context) {
        DTOConfigComing source = context.getSource();

        EConfigComing destination = EConfigComing.builder()
                .region(source.getRegion())
                .days_before(source.getDays_before())
                .days_after(source.getDays_after())
                .selected_genres(StringOR.convert(source.getSelected_genres()))
                .build();

        return destination;
    }

}

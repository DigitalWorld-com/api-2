package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.util.StringOR;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class CVTConfigComingToDTO implements Converter<EConfigComing, DTOConfigComing> {

    @Override
    public DTOConfigComing convert(MappingContext<EConfigComing, DTOConfigComing> context) {
        EConfigComing source = context.getSource();

        DTOConfigComing destination = DTOConfigComing.builder()
                .region(source.getRegion())
                .days_before(source.getDays_before())
                .days_after(source.getDays_after())
                .selected_genres(StringOR.convert(source.getSelected_genres()))
                .build();

        return destination;
    }

}

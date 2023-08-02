package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOSearch;
import com.digitalworlds.grupo2.api.entities.ESearch;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class CVTSearchToDTO implements Converter<ESearch, DTOSearch> {

    @Override
    public DTOSearch convert(MappingContext<ESearch, DTOSearch> context) {
        ESearch source = context.getSource();

        DTOSearch destination = DTOSearch.builder()
                .search_keywords(source.getSearch_keywords())
                .year_release(source.getYear_release())
                .selected_genres(UtilCvt.strORConvert(source.getSelected_genres()))
                .build();

        return destination;
    }

}

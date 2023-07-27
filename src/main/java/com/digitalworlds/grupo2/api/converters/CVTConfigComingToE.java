package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CVTConfigComingToE implements Converter<DTOConfigComing, EConfigComing> {

    @Override
    public EConfigComing convert(MappingContext<DTOConfigComing, EConfigComing> context) {
        DTOConfigComing source = context.getSource();

        //convert String [28, 53] to "28|53"
        String strSelectGenres = null;
        if (source.getSelected_genres() != null) {
            List<Integer> DTOSelectedGenres = Arrays.stream(source.getSelected_genres()).collect(Collectors.toList());
            Collections.sort(DTOSelectedGenres);
            strSelectGenres = "";
            for (Integer idGenre : DTOSelectedGenres) {
                strSelectGenres += "|" + idGenre;
            }
            strSelectGenres = strSelectGenres.substring(1);
        }

        EConfigComing destination = EConfigComing.builder()
                .region(source.getRegion())
                .days_before(source.getDays_before())
                .days_after(source.getDays_after())
                .selected_genres(strSelectGenres)
                .build();

        return destination;
    }

}

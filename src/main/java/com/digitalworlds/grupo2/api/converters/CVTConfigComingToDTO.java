package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CVTConfigComingToDTO implements Converter<EConfigComing, DTOConfigComing> {

    @Override
    public DTOConfigComing convert(MappingContext<EConfigComing, DTOConfigComing> context) {
        EConfigComing source = context.getSource();

        //convert String "28|53" to [28, 53]
        Integer[] arraySelectedGenres = null;
        if (source.getSelected_genres() != null) {
            String[] ESelectedGenres = source.getSelected_genres().split(Pattern.quote("|"));
            List<Integer> listDTOSelectedGenres = new ArrayList<>();
            for (int i = 0; i < ESelectedGenres.length; i++) {
                listDTOSelectedGenres.add(Integer.parseInt(ESelectedGenres[i]));
            }
            arraySelectedGenres = new Integer[listDTOSelectedGenres.size()];
            listDTOSelectedGenres.toArray(arraySelectedGenres);
        }

        DTOConfigComing destination = DTOConfigComing.builder()
                .region(source.getRegion())
                .days_before(source.getDays_before())
                .days_after(source.getDays_after())
                .selected_genres(arraySelectedGenres)
                .build();

        return destination;
    }

}

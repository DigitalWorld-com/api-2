package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOSearch;
import com.digitalworlds.grupo2.api.entities.ESearch;
import com.digitalworlds.grupo2.api.services.IInfo;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CVTSearchToE implements Converter<DTOSearch, ESearch> {

    IInfo iInfo;

    @Override
    public ESearch convert(MappingContext<DTOSearch, ESearch> context) {
        DTOSearch source = context.getSource();

        //Verificar que los géneros sean válidos
        iInfo.verifyGenre(source.getSelected_genres());
        //Si estan todos los generos seleccionados (seria lo mismo que 'cualquiera') se deja en null dicho campo de filtrado)
        String stringORGenres = null;
        if (!iInfo.isAllGenresSelected(source.getSelected_genres())) {
            stringORGenres = UtilCvt.strORConvert(source.getSelected_genres());
        }

        ESearch destination = ESearch.builder()
                .at(LocalDateTime.now())
                .search_keywords(source.getSearch_keywords())
                .year_release(source.getYear_release())
                .selected_genres(stringORGenres)
                .build();

        return destination;
    }

}

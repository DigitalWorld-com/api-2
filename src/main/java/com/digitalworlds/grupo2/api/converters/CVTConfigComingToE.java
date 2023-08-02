package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;
import com.digitalworlds.grupo2.api.entities.EConfigComing;
import com.digitalworlds.grupo2.api.services.IInfo;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

@AllArgsConstructor
public class CVTConfigComingToE implements Converter<DTOConfigComing, EConfigComing> {

    private IInfo iInfo;

    @Override
    public EConfigComing convert(MappingContext<DTOConfigComing, EConfigComing> context) {
        DTOConfigComing source = context.getSource();

        //Verificar que la region y género sean válidos
        iInfo.verifyCountry(source.getRegion());
        iInfo.verifyGenre(source.getSelected_genres());
        //Si estan todos los generos seleccionados (seria lo mismo que 'cualquiera') se deja en null dicho campo de filtrado)
        String stringORGenres = null;
        if (!iInfo.isAllGenresSelected(source.getSelected_genres())) {
            stringORGenres = UtilCvt.strORConvert(source.getSelected_genres());
        }

        EConfigComing destination = EConfigComing.builder()
                .region(source.getRegion())
                .days_before(source.getDays_before())
                .days_after(source.getDays_after())
                .selected_genres(stringORGenres)
                .build();

        return destination;
    }

}

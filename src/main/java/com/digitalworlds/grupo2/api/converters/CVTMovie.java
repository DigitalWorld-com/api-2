package com.digitalworlds.grupo2.api.converters;

import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.models.Movie;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class CVTMovie implements Converter<Movie, DTOMovie> {

    private static final String URL_BASE = "https://image.tmdb.org/t/p/original";

    @Override
    public DTOMovie convert(MappingContext<Movie, DTOMovie> context) {
        Movie source = context.getSource();

        DTOMovie destination = DTOMovie.builder()
                .title(source.getTitle())
                .description(source.getOverview())
                .imageURL(URL_BASE + source.getPoster_path())
                .genreIds(source.getGenre_ids()).build();

        return destination;
    }

}

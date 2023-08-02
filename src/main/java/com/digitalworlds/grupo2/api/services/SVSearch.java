package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.converters.CVTSearchToDTO;
import com.digitalworlds.grupo2.api.converters.CVTSearchToE;
import com.digitalworlds.grupo2.api.dtos.DTOMovie;
import com.digitalworlds.grupo2.api.dtos.DTOSearch;
import com.digitalworlds.grupo2.api.entities.ESearch;
import com.digitalworlds.grupo2.api.repositories.RMovie;
import com.digitalworlds.grupo2.api.repositories.RSearch;
import com.digitalworlds.grupo2.api.util.UtilCvt;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SVSearch extends SVMovie {

    private IInfo iInfo;
    private RSearch rSearch;

    public SVSearch(IHttp http, RMovie rMovie, IInfo iInfo, RSearch rSearch) {
        super(http, rMovie);
        this.iInfo = iInfo;
        this.rSearch = rSearch;
    }

    /**
     * Busca peliculas por nombre de pelicula
     *
     * @param
     */
    public List<DTOMovie> getMoviesByTitle(String movieName) {
        DTOSearch dtoSearch = DTOSearch.builder().search_keywords(movieName).build();
        return this.postSearch(dtoSearch);
    }

    public List<DTOMovie> postSearch(DTOSearch dtoSearch) {
        dtoSearch = this.setSearch(dtoSearch);

        var url = "https://api.themoviedb.org/3/search/movie?query=" + dtoSearch.getSearch_keywords()
                + "&include_adult=false"
                + "&language=es"
                + "&page=1";
        if (dtoSearch.getYear_release() != null) {
            url += "&year=" + dtoSearch.getYear_release();
        }

        List<DTOMovie> listDtoMovie = this.getMoviesByUrl(url);

        this.filterByGenres(dtoSearch, listDtoMovie);

        this.registerMovie(listDtoMovie);

        return listDtoMovie;
    }

    private void filterByGenres(DTOSearch dtoSearch, List<DTOMovie> listDtoMovie) {
        //filtro por géneros seleccionados
        if (dtoSearch.getSelected_genres() != null && dtoSearch.getSelected_genres().length > 0) {
            List<Integer> listSelectedGenres = List.of(dtoSearch.getSelected_genres());
            listDtoMovie = listDtoMovie.stream()
                    .filter(dtoMovie -> Stream.of(dtoMovie.getGenreIds()).anyMatch(listSelectedGenres::contains))
                    .collect(Collectors.toList());
        }
    }


    private DTOSearch setSearch(DTOSearch dtoSearch) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new CVTSearchToE(iInfo));
        ESearch eSearch = modelMapper.map(dtoSearch, ESearch.class);
        rSearch.save(eSearch);

        //dtoAfterSave
        UtilCvt.MODEL_MAPPER.addConverter(new CVTSearchToDTO());
        DTOSearch dtoAfterSave = UtilCvt.MODEL_MAPPER.map(eSearch, DTOSearch.class);
        return dtoAfterSave;
    }

}

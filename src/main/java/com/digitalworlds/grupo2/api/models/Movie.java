package com.digitalworlds.grupo2.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String title;
    private String overview;
    private String poster_path;
    private Integer[] genre_ids;

}

package com.digitalworlds.grupo2.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTOConfigSearch {

    Long id;
    String search_keywords;
    int year_release;
    int[] selected_genres;

}

package com.digitalworlds.grupo2.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DTOSearch {

    String search_keywords;
    Integer year_release;
    Integer[] selected_genres;

}

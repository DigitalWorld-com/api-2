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

    private String search_keywords;
    private Integer year_release;
    private Integer[] selected_genres;

}

package com.digitalworlds.grupo2.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DTOSearch {

    LocalDateTime at;
    String search_keywords;
    int year_release;
    int[] selected_genres;

}

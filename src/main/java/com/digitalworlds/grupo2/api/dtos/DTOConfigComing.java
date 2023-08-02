package com.digitalworlds.grupo2.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DTOConfigComing {

    private String region;
    private int days_before;
    private int days_after;
    private Integer[] selected_genres;

}

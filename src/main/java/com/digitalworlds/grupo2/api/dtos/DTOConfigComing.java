package com.digitalworlds.grupo2.api.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DTOConfigComing {

    String region;
    int days_before;
    int days_after;
    Integer[] selected_genres;

}

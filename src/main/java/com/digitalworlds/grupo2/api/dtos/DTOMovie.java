package com.digitalworlds.grupo2.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOMovie {

    private String title;
    private String description;
    private String imageURL;
    private Integer[] genreIds;

}

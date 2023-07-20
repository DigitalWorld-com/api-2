package com.digitalworlds.grupo2.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTOCountry {

    private String iso_3166_1;
    private String english_name;
    private String native_name;
    private byte[] countryFlag;

}

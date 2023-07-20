package com.digitalworlds.grupo2.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Country {

    private String iso_3166_1;
    private String english_name;
    private String native_name;

}

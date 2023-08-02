package com.digitalworlds.grupo2.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity(name = "CONFIG_COMING")
public class EConfigComing {

    @Id
    private String region;
    private int days_before;
    private int days_after;
    private String selected_genres;

}

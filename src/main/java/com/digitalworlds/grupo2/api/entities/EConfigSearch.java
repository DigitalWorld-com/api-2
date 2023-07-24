package com.digitalworlds.grupo2.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity(name = "CONFIG_SEARCH")
public class EConfigSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String search_keywords;
    int year_release;
    String selected_genres;

}

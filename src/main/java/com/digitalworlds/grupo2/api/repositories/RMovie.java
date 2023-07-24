package com.digitalworlds.grupo2.api.repositories;

import com.digitalworlds.grupo2.api.entities.EMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RMovie extends JpaRepository<EMovie, Long> {

    List<EMovie> findByTitle(String title);

}

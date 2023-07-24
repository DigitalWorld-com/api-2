package com.digitalworlds.grupo2.api.repositories;

import com.digitalworlds.grupo2.api.entities.EConfigSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RConfigSearch extends JpaRepository<EConfigSearch, Long> {

}

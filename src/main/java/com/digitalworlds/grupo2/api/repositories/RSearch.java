package com.digitalworlds.grupo2.api.repositories;

import com.digitalworlds.grupo2.api.entities.ESearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSearch extends JpaRepository<ESearch, Long> {

}

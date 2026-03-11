package com.fantasylol.fantasy_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fantasylol.fantasy_api.model.PuntuacionJornada;

@Repository
public interface PuntuacionRepository extends JpaRepository<PuntuacionJornada, Long> {

}
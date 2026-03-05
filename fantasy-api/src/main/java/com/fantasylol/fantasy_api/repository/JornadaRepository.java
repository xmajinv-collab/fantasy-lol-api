package com.fantasylol.fantasy_api.repository;

import com.fantasylol.fantasy_api.model.Jornada;
import com.fantasylol.fantasy_api.model.Liga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    Optional<Jornada> findByLigaAndActivaTrue(Liga liga);

    Jornada findTopByLigaOrderByFechaInicioDesc(Liga liga);
}
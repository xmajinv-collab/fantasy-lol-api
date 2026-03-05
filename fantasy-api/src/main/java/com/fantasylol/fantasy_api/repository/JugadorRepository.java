package com.fantasylol.fantasy_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Liga;

public interface JugadorRepository extends JpaRepository<Jugador,Long>{

    List<Jugador> findByEquipoIsNullAndEnMercadoTrue();

    List<Jugador> findByEquipoIsNullAndEnMercadoFalse();

    List<Jugador> findByLigaAndEquipoIsNullAndEnMercadoTrue(Liga liga);

    List<Jugador> findByEquipoId(Long equipoId);

    long countByEquipoIdAndTitularTrue(Long equipoId);

    Optional<Jugador> findByIdAndLigaId(Long jugadorId,Long ligaId);

}
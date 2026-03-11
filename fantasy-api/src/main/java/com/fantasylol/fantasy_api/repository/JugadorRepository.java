package com.fantasylol.fantasy_api.repository;

import java.time.LocalDateTime;
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
    List<Jugador> findByEnMercadoTrue();
    List<Jugador> findByEnMercadoFalse();

    long countByEquipoIdAndTitularTrue(Long equipoId);

    Optional<Jugador> findByIdAndLigaId(Long jugadorId,Long ligaId);

    
    long countByLigaIdAndEnMercadoTrue(Long ligaId);

    List<Jugador> findByLigaIdAndEnMercadoFalseAndEquipoIsNull(Long ligaId);

    List<Jugador> findByEnMercadoTrueAndFechaFinSubastaBefore(LocalDateTime fecha);

    List<Jugador> findByLigaIdAndEnMercadoTrue(Long ligaId);

    List<Jugador> findByLigaId(Long ligaId);

    List<Jugador> findByTitularTrue();
    

}
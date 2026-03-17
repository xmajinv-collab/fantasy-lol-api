package com.fantasylol.fantasy_api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasylol.fantasy_api.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    // =============================
    // MERCADO
    // =============================

    // Jugadores en mercado de una liga
    List<Jugador> findByLigaIdAndEnMercadoTrueAndEquipoIsNull(Long ligaId);

    // Jugadores fuera de mercado
    List<Jugador> findByLigaIdAndEnMercadoFalseAndEquipoIsNull(Long ligaId);

    // =============================
    // EQUIPO
    // =============================

    List<Jugador> findByEquipoId(Long equipoId);

    long countByEquipoIdAndTitularTrue(Long equipoId);

    // =============================
    // GENERALES
    // =============================

    Optional<Jugador> findByIdAndLigaId(Long jugadorId, Long ligaId);

    long countByLigaIdAndEnMercadoTrue(Long ligaId);

    List<Jugador> findByEnMercadoTrueAndFechaFinSubastaBefore(LocalDateTime fecha);

    public List<Jugador> findByLigaIdAndEnMercadoTrue(Long ligaId);

    public List<Jugador> findByTitularTrue();

    public List<Jugador> findByLigaId(Long ligaId);

   

}
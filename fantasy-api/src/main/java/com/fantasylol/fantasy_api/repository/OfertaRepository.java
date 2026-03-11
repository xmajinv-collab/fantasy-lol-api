package com.fantasylol.fantasy_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Oferta;

public interface OfertaRepository extends JpaRepository<Oferta, Long>{

    List<Oferta> findByJugadorOrderByCantidadDesc(Jugador jugador);
    
    Optional<Oferta> findTopByJugadorOrderByCantidadDesc(Jugador jugador);
}
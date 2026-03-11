package com.fantasylol.fantasy_api.repository;

import com.fantasylol.fantasy_api.model.Jornada;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.PuntuacionJornada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntuacionJornadaRepository
        extends JpaRepository<PuntuacionJornada, Long> {

    List<PuntuacionJornada> findByJornada(Jornada jornada);

    List<PuntuacionJornada> findByJugador(Jugador jugador);

    List<PuntuacionJornada> findByJornadaOrderByPuntosDesc(Jornada jornada);

    List<PuntuacionJornada> findTop5ByOrderByPuntosDesc();
}
package com.fantasylol.fantasy_api.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Jornada;
import com.fantasylol.fantasy_api.model.PuntuacionJornada;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JornadaRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.PuntuacionRepository;

@Service
public class PuntuacionService {

    private final JugadorRepository jugadorRepository;
    private final PuntuacionRepository puntuacionRepository;
    private final JornadaRepository jornadaRepository;
    private final EquipoRepository equipoRepository;

    public PuntuacionService(
            JugadorRepository jugadorRepository,
            PuntuacionRepository puntuacionRepository,
            JornadaRepository jornadaRepository,
            EquipoRepository equipoRepository) {

        this.jugadorRepository = jugadorRepository;
        this.puntuacionRepository = puntuacionRepository;
        this.jornadaRepository = jornadaRepository;
        this.equipoRepository = equipoRepository;
    }

    // =============================
    // CALCULAR JORNADA
    // =============================

    public void calcularJornada(Long jornadaId) {

        Jornada jornada = jornadaRepository
                .findById(jornadaId)
                .orElseThrow();

        // 🔥 SOLO titulares de esa liga
        List<Jugador> titulares =
        jugadorRepository.findByTitularTrue()
                .stream()
                .filter(j -> j.getLiga() != null &&
                             j.getLiga().getId().equals(jornada.getLiga().getId()))
                .toList();

        Random random = new Random();

        for (Jugador jugador : titulares) {

            // Evitar errores
            if (jugador.getEquipo() == null) continue;

            int kills = random.nextInt(10);
            int assists = random.nextInt(15);
            int deaths = random.nextInt(10);

            int puntos = (kills * 3) + (assists * 2) - deaths;

            // Guardar puntuación jornada
            PuntuacionJornada pj = new PuntuacionJornada(
                    jugador,
                    jornada,
                    kills,
                    assists,
                    deaths,
                    puntos
            );

            puntuacionRepository.save(pj);

            // 🔥 sumar al jugador
            jugador.setPuntosTotales(
                    jugador.getPuntosTotales() + puntos
            );
            jugadorRepository.save(jugador);

            // 🔥 sumar al equipo
            Equipo equipo = jugador.getEquipo();

            equipo.setPuntosTotales(
                    equipo.getPuntosTotales() + puntos
            );

            equipoRepository.save(equipo);
        }
    }
}
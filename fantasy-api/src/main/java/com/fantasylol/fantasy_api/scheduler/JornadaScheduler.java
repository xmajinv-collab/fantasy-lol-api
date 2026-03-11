package com.fantasylol.fantasy_api.scheduler;

import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

@Component
public class JornadaScheduler {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    public JornadaScheduler(
            JugadorRepository jugadorRepository,
            EquipoRepository equipoRepository) {

        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    @Scheduled(cron = "0 0 23 * * SUN")
    public void calcularPuntos() {

        List<Jugador> jugadores = jugadorRepository.findAll();

        Random random = new Random();

        for (Jugador jugador : jugadores) {

            int puntos = random.nextInt(15);

            Equipo equipo = jugador.getEquipo();

            if (equipo != null && jugador.isTitular()) {

                equipo.setPuntosTotales(
                        equipo.getPuntosTotales() + puntos
                );

                equipoRepository.save(equipo);
            }
        }
    }
}
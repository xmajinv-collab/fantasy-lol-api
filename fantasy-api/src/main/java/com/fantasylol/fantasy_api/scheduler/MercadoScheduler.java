package com.fantasylol.fantasy_api.scheduler;

import java.util.Collections;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

@Component
public class MercadoScheduler {

    private final JugadorRepository jugadorRepository;

    public MercadoScheduler(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void generarMercado() {

        List<Jugador> jugadores =
                jugadorRepository.findByEquipoIsNullAndEnMercadoFalse();

        Collections.shuffle(jugadores);

        jugadores.stream()
                .limit(5)
                .forEach(jugador -> {

                    jugador.setEnMercado(true);
                    jugadorRepository.save(jugador);

                });
    }
}
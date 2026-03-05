package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.model.Jornada;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.PuntuacionJornada;
import com.fantasylol.fantasy_api.repository.JornadaRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.PuntuacionJornadaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final PuntuacionJornadaRepository puntuacionRepository;
    private final JornadaRepository jornadaRepository;
    private final JugadorRepository jugadorRepository;

    public EstadisticasController(PuntuacionJornadaRepository puntuacionRepository,
                                   JornadaRepository jornadaRepository,
                                   JugadorRepository jugadorRepository) {
        this.puntuacionRepository = puntuacionRepository;
        this.jornadaRepository = jornadaRepository;
        this.jugadorRepository = jugadorRepository;
    }

    // ===============================
    // ESTADÍSTICAS POR JORNADA
    // ===============================
    @GetMapping("/jornada/{jornadaId}")
    public List<PuntuacionJornada> verPorJornada(@PathVariable Long jornadaId) {

        Jornada jornada = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new RuntimeException("Jornada no encontrada"));

        return puntuacionRepository.findByJornada(jornada);
    }

    // ===============================
    // HISTORIAL DE UN JUGADOR
    // ===============================
    @GetMapping("/jugador/{jugadorId}")
    public List<PuntuacionJornada> verPorJugador(@PathVariable Long jugadorId) {

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        return puntuacionRepository.findByJugador(jugador);
    }

    // ===============================
    // MVP DE UNA JORNADA
    // ===============================
    @GetMapping("/mvp/{jornadaId}")
    public PuntuacionJornada verMVP(@PathVariable Long jornadaId) {

        Jornada jornada = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new RuntimeException("Jornada no encontrada"));

        List<PuntuacionJornada> lista =
                puntuacionRepository.findByJornadaOrderByPuntosDesc(jornada);

        if (lista.isEmpty()) {
            throw new RuntimeException("No hay puntuaciones para esta jornada");
        }

        return lista.get(0);
    }
}
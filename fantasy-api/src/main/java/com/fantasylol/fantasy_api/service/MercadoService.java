package com.fantasylol.fantasy_api.service;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Oferta;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.OfertaRepository;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoService {

    private final JugadorRepository jugadorRepository;
    private final OfertaRepository ofertaRepository;
    private final EquipoRepository equipoRepository;
    private final LigaRepository ligaRepository;

    public MercadoService(JugadorRepository jugadorRepository,
                          OfertaRepository ofertaRepository,
                          EquipoRepository equipoRepository,
                          LigaRepository ligaRepository) {


        this.jugadorRepository = jugadorRepository;
        this.ofertaRepository = ofertaRepository;
        this.equipoRepository = equipoRepository;
        this.ligaRepository = ligaRepository;
    }

    public void mantenimientoMercado(Long ligaId) {

        long jugadoresEnMercado =
                jugadorRepository.countByLigaIdAndEnMercadoTrue(ligaId);

        int mercadoObjetivo = 20;

        if (jugadoresEnMercado >= mercadoObjetivo) {
            return;
        }

        List<Jugador> disponibles =
                jugadorRepository.findByLigaIdAndEnMercadoFalseAndEquipoIsNull(ligaId);

        Collections.shuffle(disponibles);

        int faltan = mercadoObjetivo - (int) jugadoresEnMercado;

        disponibles.stream()
                .limit(faltan)
                .forEach(jugador -> {

                    jugador.setEnMercado(true);

                    jugador.setFechaFinSubasta(
                            LocalDateTime.now().plusHours(12)
                    );

                    jugadorRepository.save(jugador);
                });
    }

    public void resolverSubastas() {

        List<Jugador> jugadores =
                jugadorRepository.findByEnMercadoTrueAndFechaFinSubastaBefore(LocalDateTime.now());

        for (Jugador jugador : jugadores) {

            Optional<Oferta> mejorOferta =
                    ofertaRepository.findTopByJugadorOrderByCantidadDesc(jugador);

            if (mejorOferta.isPresent()) {

                Oferta oferta = mejorOferta.get();

                Equipo equipo = oferta.getEquipo();

                equipo.setPresupuesto(
                        equipo.getPresupuesto() - oferta.getCantidad()
                );

                equipoRepository.save(equipo);

                jugador.setEquipo(equipo);
            }

            jugador.setEnMercado(false);

            jugadorRepository.save(jugador);
        }
    }

    public void generarMercadoDiario() {
        List<Liga> ligas = ligaRepository.findAll();
        for (Liga liga : ligas){ List<Jugador> jugadores=
            jugadorRepository.findByLigaId(liga.getId());

            Collections.shuffle(jugadores);

            jugadores.stream()
            .limit(5)
            .forEach(jugador -> {
                jugador.setEnMercado(true);
                jugadorRepository.save(jugador);
        });
        }
    }
}

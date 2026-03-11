package com.fantasylol.fantasy_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

import jakarta.transaction.Transactional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;

    public EquipoService(
            EquipoRepository equipoRepository,
            JugadorRepository jugadorRepository) {

        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;

    }

    // ============================
    // OBTENER EQUIPO
    // ============================

    public Equipo obtenerEquipo(Long id) {

        return equipoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipo no encontrado"));

    }

    // ============================
    // JUGADORES DEL EQUIPO
    // ============================

    public List<Jugador> jugadoresEquipo(Long equipoId) {

        return jugadorRepository.findByEquipoId(equipoId);

    }

    // ============================
    // PONER TITULAR
    // ============================

    public void ponerTitular(Long jugadorId) {

        Jugador jugador =
                jugadorRepository.findById(jugadorId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Jugador no encontrado"));

        jugador.setTitular(!jugador.isTitular());

        jugadorRepository.save(jugador);

    }

    // ============================
    // ALINEAR JUGADOR CON SEGURIDAD
    // ============================

    @Transactional
    public void alinearJugador(Long equipoId, Long jugadorId, String username) {

        Equipo equipo = equipoRepository
                .findById(equipoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipo no encontrado"));

        Jugador jugador = jugadorRepository
                .findById(jugadorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Jugador no encontrado"));

        if (!jugador.getEquipo().getId().equals(equipoId)) {
            throw new IllegalStateException("El jugador no pertenece al equipo");
        }

        long titulares = jugadorRepository
                .countByEquipoIdAndTitularTrue(equipoId);

        if (!jugador.isTitular() && titulares >= 5) {
            throw new IllegalStateException("Solo puedes tener 5 titulares");
        }

        jugador.setTitular(!jugador.isTitular());

        jugadorRepository.save(jugador);
    }

}
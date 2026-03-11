package com.fantasylol.fantasy_api.service;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Oferta;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.OfertaRepository;

import jakarta.transaction.Transactional;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    public OfertaService(
            OfertaRepository ofertaRepository,
            JugadorRepository jugadorRepository,
            EquipoRepository equipoRepository) {

        this.ofertaRepository = ofertaRepository;
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    @Transactional
    public void aceptarOferta(Long ofertaId) {

        Oferta oferta = ofertaRepository
                .findById(ofertaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Oferta no encontrada"));

        Jugador jugador = oferta.getJugador();
        Equipo comprador = oferta.getEquipo();

        if (comprador.getPresupuesto() < oferta.getCantidad()) {
            throw new IllegalStateException("No tienes presupuesto suficiente");
        }

        comprador.setPresupuesto(
                comprador.getPresupuesto() - oferta.getCantidad()
        );

        jugador.setEquipo(comprador);
        jugador.setEnMercado(false);

        equipoRepository.save(comprador);
        jugadorRepository.save(jugador);

        ofertaRepository.delete(oferta);
    }
    @Transactional
    public Oferta crearOferta(Long jugadorId, double cantidad, String username) {

    Jugador jugador = jugadorRepository
            .findById(jugadorId)
            .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

    Equipo equipo = equipoRepository
            .findByUsuarioUsername(username)
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

    Oferta oferta = new Oferta();

    oferta.setJugador(jugador);
    oferta.setEquipo(equipo);
    oferta.setCantidad(cantidad);

    return ofertaRepository.save(oferta);
}
}
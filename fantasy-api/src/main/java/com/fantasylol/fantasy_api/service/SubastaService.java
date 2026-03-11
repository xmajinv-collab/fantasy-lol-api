package com.fantasylol.fantasy_api.service;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Oferta;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.OfertaRepository;

@Service
public class SubastaService {

    private final OfertaRepository ofertaRepository;
    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    public SubastaService(
            OfertaRepository ofertaRepository,
            JugadorRepository jugadorRepository,
            EquipoRepository equipoRepository){

        this.ofertaRepository = ofertaRepository;
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;

    }

    public void pujar(Long jugadorId, Long equipoId, double cantidad){

        Jugador jugador =
                jugadorRepository.findById(jugadorId).orElseThrow();

        Equipo equipo =
                equipoRepository.findById(equipoId).orElseThrow();

        Oferta oferta = new Oferta();

        oferta.setJugador(jugador);
        oferta.setEquipo(equipo);
        oferta.setCantidad(cantidad);

        ofertaRepository.save(oferta);

    }

}
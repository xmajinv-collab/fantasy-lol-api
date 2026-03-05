package com.fantasylol.fantasy_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

@Service
public class MercadoService {

    private final JugadorRepository jugadorRepository;

    public MercadoService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public List<Jugador> obtenerMercado(Long ligaId) {

        return jugadorRepository.findByEquipoIsNullAndEnMercadoTrue();
    }
}
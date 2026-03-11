package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mercado")
public class MercadoController {

    private final JugadorRepository jugadorRepository;

    public MercadoController(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @GetMapping("/{ligaId}")
    public List<Jugador> obtenerMercado(@PathVariable Long ligaId) {

        return jugadorRepository.findByLigaIdAndEnMercadoTrue(ligaId);

    }
}
package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugadores")
@CrossOrigin
public class JugadorController {

    private final JugadorRepository jugadorRepository;

    public JugadorController(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @GetMapping
    public List<Jugador> listarJugadores() {
        return jugadorRepository.findAll();
    }
}
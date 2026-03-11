package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.service.EquipoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // ============================
    // OBTENER EQUIPO
    // ============================

    @GetMapping("/{equipoId}")
    public ResponseEntity<Equipo> obtenerEquipo(@PathVariable Long equipoId) {

        return ResponseEntity.ok(
                equipoService.obtenerEquipo(equipoId)
        );

    }

    // ============================
    // JUGADORES DEL EQUIPO
    // ============================

    @GetMapping("/{equipoId}/jugadores")
    public ResponseEntity<List<Jugador>> jugadoresEquipo(@PathVariable Long equipoId) {

        return ResponseEntity.ok(
                equipoService.jugadoresEquipo(equipoId)
        );

    }

    // ============================
    // PONER / QUITAR TITULAR
    // ============================

    @PostMapping("/titular/{jugadorId}")
    public ResponseEntity<?> ponerTitular(@PathVariable Long jugadorId) {

        equipoService.ponerTitular(jugadorId);

        return ResponseEntity.ok("Jugador actualizado");

    }

}
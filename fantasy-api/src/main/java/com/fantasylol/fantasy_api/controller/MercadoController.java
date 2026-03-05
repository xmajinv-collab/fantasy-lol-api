package com.fantasylol.fantasy_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.service.MercadoService;

@RestController
@RequestMapping("/api/mercado")
public class MercadoController {

    private final MercadoService mercadoService;

    public MercadoController(MercadoService mercadoService) {
        this.mercadoService = mercadoService;
    }

    @GetMapping("/{ligaId}")
    public ResponseEntity<List<Jugador>> obtenerMercado(@PathVariable Long ligaId) {

        return ResponseEntity.ok(
                mercadoService.obtenerMercado(ligaId)
        );
    }
}
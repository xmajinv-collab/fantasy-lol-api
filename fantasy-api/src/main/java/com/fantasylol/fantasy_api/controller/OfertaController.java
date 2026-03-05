package com.fantasylol.fantasy_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.model.Oferta;
import com.fantasylol.fantasy_api.service.OfertaService;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService){
        this.ofertaService = ofertaService;
    }

    @PostMapping("/{jugadorId}")
    public ResponseEntity<Oferta> ofertar(
            @PathVariable Long jugadorId,
            @RequestParam double cantidad){

        String username =
                SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                ofertaService.crearOferta(jugadorId,cantidad,username)
        );
    }
}
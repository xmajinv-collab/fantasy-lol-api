package com.fantasylol.fantasy_api.controller;

import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.service.PuntuacionService;

@RestController
@RequestMapping("/api/jornadas")
public class JornadaController {

    private final PuntuacionService puntuacionService;

    public JornadaController(PuntuacionService puntuacionService) {
        this.puntuacionService = puntuacionService;
    }

    @PostMapping("/{jornadaId}/calcular")
    public String calcularJornada(@PathVariable Long jornadaId) {

        puntuacionService.calcularJornada(jornadaId);

        return "Jornada calculada correctamente";
    }
}
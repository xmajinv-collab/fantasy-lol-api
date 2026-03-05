package com.fantasylol.fantasy_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.service.JornadaService;

@RestController
@RequestMapping("/api/jornadas")
public class JornadaController {

    private final JornadaService jornadaService;

    public JornadaController(JornadaService jornadaService){
        this.jornadaService = jornadaService;
    }

    @PostMapping("/{ligaId}/cerrar")
    public ResponseEntity<String> cerrarJornada(@PathVariable Long ligaId){

        jornadaService.cerrarJornada(ligaId);

        return ResponseEntity.ok("Jornada cerrada correctamente");
    }
}
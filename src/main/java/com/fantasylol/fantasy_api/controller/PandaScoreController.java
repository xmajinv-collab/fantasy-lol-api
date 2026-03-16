package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.service.PandaScoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pandascore")
public class PandaScoreController {

    private final PandaScoreService pandaScoreService;

    public PandaScoreController(PandaScoreService pandaScoreService) {
        this.pandaScoreService = pandaScoreService;
    }

    @GetMapping("/importar-jugadores")
    public String importarJugadores() {

        pandaScoreService.importarJugadores();

        return "Jugadores importados correctamente";
    }
}
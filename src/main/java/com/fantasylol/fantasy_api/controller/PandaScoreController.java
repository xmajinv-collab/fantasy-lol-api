package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.service.PlayerSyncService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pandascore")
public class PandaScoreController {

    private final PlayerSyncService playerSyncService;

    public PandaScoreController(PlayerSyncService playerSyncService) {
        this.playerSyncService = playerSyncService;
    }

    @GetMapping("/sync/{ligaId}")
    public String sync(@PathVariable Long ligaId) {

        playerSyncService.sincronizarJugadores(ligaId);

        return "Jugadores sincronizados correctamente";
    }
}
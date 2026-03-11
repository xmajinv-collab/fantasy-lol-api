package com.fantasylol.fantasy_api.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fantasylol.fantasy_api.service.PlayerSyncService;

@Component
public class PlayerSyncScheduler {

    private final PlayerSyncService playerSyncService;

    public PlayerSyncScheduler(PlayerSyncService playerSyncService) {
        this.playerSyncService = playerSyncService;
    }

    // Cada día a las 4 AM
    @Scheduled(cron = "0 0 4 * * *")
    public void syncPlayers() {

        playerSyncService.sincronizarJugadores();

    }

}
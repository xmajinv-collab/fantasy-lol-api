package com.fantasylol.fantasy_api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.dto.LigaDashboardDTO;
import com.fantasylol.fantasy_api.dto.RankingDTO;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.service.LigaService;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;

    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    // ============================
    // CREAR LIGA
    // ============================

    @PostMapping
    public ResponseEntity<Liga> crearLiga(
            @RequestParam String nombre,
            Principal principal) {

        Liga liga = ligaService.crearLiga(nombre, principal.getName());

        return ResponseEntity.ok(liga);
    }

    // ============================
    // UNIRSE A LIGA
    // ============================

    @PostMapping("/unirse")
    public ResponseEntity<?> unirseLiga(
            @RequestParam String codigo,
            Principal principal) {

        ligaService.unirseLiga(codigo, principal.getName());

        return ResponseEntity.ok("Te has unido a la liga");
    }

    // ============================
    // LISTAR LIGAS DEL USUARIO
    // ============================

    @GetMapping
    public ResponseEntity<List<Liga>> listarLigasUsuario(
            Principal principal) {

        List<Liga> ligas =
                ligaService.listarLigasPorUsuario(principal.getName());

        return ResponseEntity.ok(ligas);
    }

    // ============================
    // OBTENER LIGA
    // ============================

    @GetMapping("/{ligaId}")
    public ResponseEntity<Liga> obtenerLiga(
            @PathVariable Long ligaId) {

        Liga liga = ligaService.obtenerLiga(ligaId);

        return ResponseEntity.ok(liga);
    }

    // ============================
    // RANKING
    // ============================

    @GetMapping("/{ligaId}/ranking")
    public ResponseEntity<List<RankingDTO>> ranking(
            @PathVariable Long ligaId) {

        List<RankingDTO> ranking =
                ligaService.obtenerRanking(ligaId);

        return ResponseEntity.ok(ranking);
    }

    // ============================
    // DASHBOARD LIGA
    // ============================

    @GetMapping("/{ligaId}/dashboard")
    public ResponseEntity<LigaDashboardDTO> dashboard(
            @PathVariable Long ligaId,
            Principal principal) {

        LigaDashboardDTO dashboard =
                ligaService.obtenerDashboard(ligaId, principal.getName());

        return ResponseEntity.ok(dashboard);
    }

}
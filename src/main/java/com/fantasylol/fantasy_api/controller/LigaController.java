package com.fantasylol.fantasy_api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.dto.LigaDashboardDTO;
import com.fantasylol.fantasy_api.dto.RankingDTO;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.service.LigaService;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;
    private final JugadorRepository jugadorRepository;

    public LigaController(LigaService ligaService, JugadorRepository jugadorRepository) {
        this.ligaService = ligaService;
        this.jugadorRepository = jugadorRepository;
    }

    // ============================
    // CREAR LIGA
    // ============================

    @PostMapping
    public ResponseEntity<Liga> crearLiga(
            @RequestParam String nombre,
            Principal principal
    ) {

        Liga liga = ligaService.crearLiga(nombre, principal.getName());

        return ResponseEntity.ok(liga);
    }

    // ============================
    // UNIRSE A LIGA
    // ============================

    @PostMapping("/unirse")
    public ResponseEntity<String> unirseLiga(
            @RequestParam String codigo,
            Principal principal
    ) {

        ligaService.unirseLiga(codigo, principal.getName());

        return ResponseEntity.ok("Te has unido a la liga");
    }

    // ============================
    // LISTAR LIGAS DEL USUARIO
    // ============================

    @GetMapping
    public ResponseEntity<List<Liga>> listarLigasUsuario(
            Principal principal
    ) {

        List<Liga> ligas =
                ligaService.listarLigasPorUsuario(principal.getName());

        return ResponseEntity.ok(ligas);
    }

    // ============================
    // OBTENER LIGA
    // ============================

    @GetMapping("/{ligaId}")
    public ResponseEntity<Liga> obtenerLiga(
            @PathVariable Long ligaId
    ) {

        Liga liga = ligaService.obtenerLiga(ligaId);

        return ResponseEntity.ok(liga);
    }

    // ============================
    // RANKING DE LIGA
    // ============================

    @GetMapping("/{ligaId}/ranking")
    public ResponseEntity<List<RankingDTO>> ranking(
            @PathVariable Long ligaId
    ) {

        List<RankingDTO> ranking =
                ligaService.obtenerRanking(ligaId);

        return ResponseEntity.ok(ranking);
    }

    // ============================
    // DASHBOARD DE LIGA
    // ============================

    @GetMapping("/{ligaId}/dashboard")
    public ResponseEntity<LigaDashboardDTO> dashboard(
            @PathVariable Long ligaId,
            Principal principal
    ) {

        LigaDashboardDTO dashboard =
                ligaService.obtenerDashboard(
                        ligaId,
                        principal.getName()
                );

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/{ligaId}/mercado")
    public ResponseEntity<List<Jugador>> mercado(
            @PathVariable Long ligaId
    ) {

        List<Jugador> mercado =
                jugadorRepository.findByLigaIdAndEnMercadoTrue(ligaId);

        return ResponseEntity.ok(mercado);
    }
}
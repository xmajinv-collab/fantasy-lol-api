package com.fantasylol.fantasy_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.fantasylol.fantasy_api.dto.ApiResponse;
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

    // ==============================
    // OBTENER USERNAME AUTENTICADO
    // ==============================

    private String getUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    // ==============================
    // CREAR LIGA
    // ==============================

    @PostMapping
    public ResponseEntity<ApiResponse<Liga>> crearLiga(
            @RequestParam String nombre) {

        Liga liga = ligaService.crearLiga(
                nombre,
                getUsername()
        );

        return ResponseEntity
                .status(201)
                .body(ApiResponse.success(
                        "Liga creada correctamente",
                        liga
                ));
    }

    // ==============================
    // LISTAR LIGAS DEL USUARIO
    // ==============================

    @GetMapping
    public ResponseEntity<ApiResponse<List<Liga>>> listarLigas() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ligaService.listarLigasPorUsuario(getUsername())
                )
        );
    }

    // ==============================
    // OBTENER LIGA POR ID
    // ==============================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Liga>> obtenerLiga(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ligaService.obtenerLiga(id)
                )
        );
    }

    // ==============================
    // ELIMINAR LIGA
    // ==============================

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarLiga(
            @PathVariable Long id) {

        ligaService.eliminarLiga(id);

        return ResponseEntity.ok(
                ApiResponse.success("Liga eliminada correctamente")
        );
    }

    // ==============================
    // RANKING
    // ==============================

    @GetMapping("/{ligaId}/ranking")
    public ResponseEntity<ApiResponse<List<RankingDTO>>> ranking(
            @PathVariable Long ligaId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ligaService.obtenerRanking(ligaId)
                )
        );
    }

    // ==============================
    // DASHBOARD
    // ==============================

    @GetMapping("/{ligaId}/dashboard")
    public ResponseEntity<ApiResponse<LigaDashboardDTO>> dashboard(
            @PathVariable Long ligaId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ligaService.obtenerDashboard(
                                ligaId,
                                getUsername()
                        )
                )
        );
    }

    // ==============================
    // UNIRSE A LIGA
    // ==============================

    @PostMapping("/unirse")
    public ResponseEntity<ApiResponse<String>> unirseLiga(
            @RequestParam String codigo) {

        ligaService.unirseLiga(
                codigo,
                getUsername()
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Te has unido a la liga correctamente"
                )
        );
    }
    
    @PostMapping("/{ligaId}/codigo")
    public ResponseEntity<String> generarCodigo(
            @PathVariable Long ligaId) {

        return ResponseEntity.ok(
                ligaService.generarCodigoInvitacion(ligaId)
        );
    }
}
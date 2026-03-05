package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.dto.ApiResponse;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.service.EquipoService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // ===============================
    // OBTENER USERNAME
    // ===============================

    private String getUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    // ===============================
    // CREAR EQUIPO
    // ===============================

    @PostMapping
    public ResponseEntity<ApiResponse<Equipo>> crearEquipo(
            @RequestParam String nombre,
            @RequestParam Long ligaId) {

        Equipo equipo = equipoService.crearEquipo(
                nombre,
                ligaId,
                getUsername()
        );

        return ResponseEntity.status(201)
                .body(ApiResponse.success(
                        "Equipo creado correctamente",
                        equipo
                ));
    }

    // ===============================
    // LISTAR EQUIPOS DEL USUARIO
    // ===============================

    @GetMapping
    public ResponseEntity<ApiResponse<List<Equipo>>> listarEquipos() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        equipoService.listarEquiposDelUsuario(
                                getUsername()
                        )
                )
        );
    }

    // ===============================
    // LISTAR EQUIPOS POR LIGA
    // ===============================

    @GetMapping("/liga/{ligaId}")
    public ResponseEntity<ApiResponse<List<Equipo>>> listarEquiposPorLiga(
            @PathVariable Long ligaId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        equipoService.listarEquiposPorLiga(
                                ligaId,
                                getUsername()
                        )
                )
        );
    }

    // ===============================
    // OBTENER EQUIPO
    // ===============================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Equipo>> obtenerEquipo(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        equipoService.obtenerEquipoSeguro(
                                id,
                                getUsername()
                        )
                )
        );
    }

    // ===============================
    // ELIMINAR EQUIPO
    // ===============================

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarEquipo(
            @PathVariable Long id) {

        equipoService.eliminarEquipo(
                id,
                getUsername()
        );

        return ResponseEntity.ok(
                ApiResponse.success("Equipo eliminado correctamente")
        );
    }

    // ===============================
    // FICHAR JUGADOR
    // ===============================

    @PostMapping("/{equipoId}/fichar/{jugadorId}")
    public ResponseEntity<ApiResponse<Void>> ficharJugador(
            @PathVariable Long equipoId,
            @PathVariable Long jugadorId) {

        equipoService.ficharJugador(
                equipoId,
                jugadorId,
                getUsername()
        );

        return ResponseEntity.ok(
                ApiResponse.success("Jugador fichado correctamente")
        );
    }

    // ===============================
    // VENDER JUGADOR
    // ===============================

    @PostMapping("/{equipoId}/vender/{jugadorId}")
    public ResponseEntity<ApiResponse<Void>> venderJugador(
            @PathVariable Long equipoId,
            @PathVariable Long jugadorId) {

        equipoService.venderJugador(
                equipoId,
                jugadorId,
                getUsername()
        );

        return ResponseEntity.ok(
                ApiResponse.success("Jugador vendido correctamente")
        );
    }

    // ===============================
    // ALINEAR TITULAR
    // ===============================

    @PostMapping("/{equipoId}/alinear/{jugadorId}")
    public ResponseEntity<ApiResponse<Void>> alinearJugador(
            @PathVariable Long equipoId,
            @PathVariable Long jugadorId) {

        equipoService.alinearJugador(
                equipoId,
                jugadorId,
                getUsername()
        );

        return ResponseEntity.ok(
                ApiResponse.success("Titularidad actualizada")
        );
    }
}
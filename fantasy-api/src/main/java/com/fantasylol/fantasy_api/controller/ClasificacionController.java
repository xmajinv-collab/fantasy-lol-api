package com.fantasylol.fantasy_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fantasylol.fantasy_api.dto.ClasificacionDTO;
import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;

@RestController
@RequestMapping("/clasificacion")
public class ClasificacionController {

    private static final Logger logger =
            LoggerFactory.getLogger(ClasificacionController.class);

    private final EquipoRepository equipoRepository;
    private final LigaRepository ligaRepository;

    public ClasificacionController(EquipoRepository equipoRepository,
                                   LigaRepository ligaRepository) {
        this.equipoRepository = equipoRepository;
        this.ligaRepository = ligaRepository;
    }

    @GetMapping("/{ligaId}")
    public List<ClasificacionDTO> verClasificacion(@PathVariable Long ligaId) {

        Liga liga = ligaRepository.findById(ligaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Liga no encontrada"));

        logger.debug("Obteniendo clasificación de liga {}", ligaId);

        List<Equipo> equipos =
                equipoRepository.findByLigaOrderByPuntosTotalesDesc(liga);

        List<ClasificacionDTO> resultado = new ArrayList<>();

        if (equipos.isEmpty()) {
            return resultado;
        }

        int puntosLider = equipos.get(0).getPuntosTotales();

        for (int i = 0; i < equipos.size(); i++) {

            Equipo equipo = equipos.get(i);

            int diferencia = puntosLider - equipo.getPuntosTotales();

            resultado.add(
                    new ClasificacionDTO(
                            i + 1,
                            equipo.getNombre(),
                            equipo.getPuntosTotales(),
                            diferencia
                    )
            );
        }

        return resultado;
    }
}
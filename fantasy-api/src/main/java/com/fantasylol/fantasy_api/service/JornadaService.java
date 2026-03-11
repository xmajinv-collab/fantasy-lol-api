package com.fantasylol.fantasy_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;

@Service
public class JornadaService {

    private final LigaRepository ligaRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;

    public JornadaService(
            LigaRepository ligaRepository,
            EquipoRepository equipoRepository,
            JugadorRepository jugadorRepository){

        this.ligaRepository = ligaRepository;
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    public void cerrarJornada(Long ligaId){

        Liga liga = ligaRepository
                .findById(ligaId)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        List<Equipo> equipos =
                equipoRepository.findByLigaIdOrderByPuntosTotalesDesc(ligaId);

        for(Equipo equipo : equipos){

            long titulares =
                    jugadorRepository.countByEquipoIdAndTitularTrue(equipo.getId());

            if(titulares < 5){
                throw new IllegalStateException("El equipo debe tener 5 titulares");
            }
        }

        liga.setJornadaActiva(false);

        ligaRepository.save(liga);
    }
}
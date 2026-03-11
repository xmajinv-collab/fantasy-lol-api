package com.fantasylol.fantasy_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.dto.LigaDashboardDTO;
import com.fantasylol.fantasy_api.dto.RankingDTO;
import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

import jakarta.transaction.Transactional;

@Service
public class LigaService {

    private final LigaRepository ligaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    public LigaService(
            LigaRepository ligaRepository,
            UsuarioRepository usuarioRepository,
            EquipoRepository equipoRepository,
            JugadorRepository jugadorRepository){


        this.ligaRepository = ligaRepository;
        this.usuarioRepository = usuarioRepository;
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
    }

    // CREAR LIGA

    public Liga crearLiga(String nombre, String username){

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Liga liga = new Liga();
        liga.setNombre(nombre);
        liga.setCodigoInvitacion(UUID.randomUUID().toString());
        liga.setUsuario(usuario);
        liga.setJornadaActiva(false);

        return ligaRepository.save(liga);
    }

    // LISTAR LIGAS

    public List<Liga> listarLigasPorUsuario(String username){

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return ligaRepository.findByUsuario(usuario);
    }

    // OBTENER LIGA

    public Liga obtenerLiga(Long id){

        return ligaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));
    }

    // ELIMINAR LIGA

    public void eliminarLiga(Long id){

        Liga liga = ligaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        ligaRepository.delete(liga);
    }

    // UNIRSE A LIGA

    @Transactional
    public void unirseLiga(String codigo, String username){

        Liga liga = ligaRepository.findByCodigoInvitacion(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Equipo equipo = new Equipo();
        equipo.setNombre("Equipo de " + usuario.getUsername());
        equipo.setPresupuesto(10000000);
        equipo.setPuntosTotales(0);
        equipo.setLiga(liga);
        equipo.setUsuario(usuario);

        equipoRepository.save(equipo);
    }

    // GENERAR CODIGO INVITACION

    public String generarCodigoInvitacion(Long ligaId){

        Liga liga = ligaRepository.findById(ligaId)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        String codigo = UUID.randomUUID().toString();

        liga.setCodigoInvitacion(codigo);

        ligaRepository.save(liga);

        return codigo;
    }
    // RANKING DE LA LIGA

public List<RankingDTO> obtenerRanking(Long ligaId) {

    List<Equipo> equipos = equipoRepository.findByLigaId(ligaId);

    List<RankingDTO> ranking = new ArrayList<>();

    for (Equipo equipo : equipos) {

        int puntosEquipo = equipo.getJugadores().stream()
                .filter(Jugador::isTitular)
                .mapToInt(Jugador::getPuntosTotales)
                .sum();

        ranking.add(new RankingDTO(
                equipo.getNombre(),
                puntosEquipo
        ));
    }

    ranking.sort((a, b) -> Integer.compare(b.getPuntos(), a.getPuntos()));

    return ranking;
}


    // DASHBOARD DE USUARIO

   public LigaDashboardDTO obtenerDashboard(Long ligaId, String username) {

    Liga liga = ligaRepository
            .findById(ligaId)
            .orElseThrow(() ->
                    new RuntimeException("Liga no encontrada"));

    List<RankingDTO> ranking = obtenerRanking(ligaId);

    List<Equipo> equipos =
            equipoRepository.findByLigaId(ligaId);

    List<Jugador> mercado =
            jugadorRepository.findByLigaIdAndEnMercadoTrue(ligaId);

    return new LigaDashboardDTO(
            liga.getNombre(),
            ranking,
            equipos,
            mercado
    );
}
}
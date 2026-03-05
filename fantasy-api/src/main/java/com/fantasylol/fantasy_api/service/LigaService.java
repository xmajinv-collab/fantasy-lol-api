package com.fantasylol.fantasy_api.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.dto.LigaDashboardDTO;
import com.fantasylol.fantasy_api.dto.RankingDTO;
import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;
import com.fantasylol.fantasy_api.dto.LigaDashboardDTO;

import jakarta.transaction.Transactional;

@Service
public class LigaService {

    private final LigaRepository ligaRepository;
    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;

    public LigaService(
            LigaRepository ligaRepository,
            EquipoRepository equipoRepository,
            UsuarioRepository usuarioRepository) {

        this.ligaRepository = ligaRepository;
        this.equipoRepository = equipoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // =====================================
    // CREAR LIGA
    // =====================================

    @Transactional
    public Liga crearLiga(String nombre, String username) {

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Liga liga = new Liga();
        liga.setNombre(nombre);
        liga.setUsuario(usuario);
        liga.setJornadaActiva(false);

        ligaRepository.save(liga);

        // Crear equipo automáticamente
        Equipo equipo = new Equipo("Equipo de " + username, 10000000);
        equipo.setUsuario(usuario);
        equipo.setLiga(liga);

        equipoRepository.save(equipo);

        return liga;
    }

    // =====================================
    // LISTAR LIGAS DEL USUARIO
    // =====================================

    public List<Liga> listarLigasPorUsuario(String username) {

        return ligaRepository.findByUsuarioUsername(username);
    }

    // =====================================
    // OBTENER LIGA
    // =====================================

    public Liga obtenerLiga(Long id) {

        return ligaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));
    }

    // =====================================
    // ELIMINAR LIGA
    // =====================================

    public void eliminarLiga(Long id) {

        Liga liga = obtenerLiga(id);

        ligaRepository.delete(liga);
    }

    // =====================================
    // UNIRSE A LIGA
    // =====================================

    @Transactional
    public void unirseLiga(String codigoInvitacion, String username) {

        Liga liga = ligaRepository
                .findByCodigoInvitacion(codigoInvitacion)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Equipo equipo = new Equipo("Equipo de " + username, 10000000);
        equipo.setUsuario(usuario);
        equipo.setLiga(liga);

        equipoRepository.save(equipo);
    }

    // =====================================
    // RANKING
    // =====================================

    public List<RankingDTO> obtenerRanking(Long ligaId) {

        List<Equipo> equipos =
                equipoRepository.findByLigaIdOrderByPuntosTotalesDesc(ligaId);

        AtomicInteger posicion = new AtomicInteger(1);

        return equipos.stream()
                .map(e -> new RankingDTO(
                        posicion.getAndIncrement(),
                        e.getNombre(),
                        e.getPuntosTotales(),
                        e.getPresupuesto(),
                        e.getJugadores().size()
                ))
                .collect(Collectors.toList());
    }
   public LigaDashboardDTO obtenerDashboard(Long ligaId, String username){

    Liga liga = ligaRepository
            .findById(ligaId)
            .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        return new LigaDashboardDTO(
                liga.getId(),
                liga.getNombre(),
                liga.isJornadaActiva()
        );
        }
        public String generarCodigoInvitacion(Long ligaId) {

            Liga liga = ligaRepository
                    .findById(ligaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

            String codigo = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                liga.setCodigoInvitacion(codigo);
                ligaRepository.save(liga);

            return codigo;
        }
}
package com.fantasylol.fantasy_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.EquipoRepository;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final LigaRepository ligaRepository;
    private final UsuarioRepository usuarioRepository;

    public EquipoService(
            EquipoRepository equipoRepository,
            JugadorRepository jugadorRepository,
            LigaRepository ligaRepository,
            UsuarioRepository usuarioRepository) {

        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
        this.ligaRepository = ligaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ============================
    // CREAR EQUIPO
    // ============================

    @Transactional
    public Equipo crearEquipo(String nombre, Long ligaId, String username) {

        Liga liga = ligaRepository
                .findById(ligaId)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setPresupuesto(10000000);
        equipo.setPuntosTotales(0);
        equipo.setLiga(liga);
        equipo.setUsuario(usuario);

        return equipoRepository.save(equipo);
    }

    // ============================
    // LISTAR EQUIPOS DEL USUARIO
    // ============================

    public List<Equipo> listarEquiposDelUsuario(String username) {

        return equipoRepository.findByLigaUsuarioUsername(username);
    }

    // ============================
    // LISTAR EQUIPOS POR LIGA
    // ============================

    public List<Equipo> listarEquiposPorLiga(Long ligaId, String username) {

        Liga liga = ligaRepository
                .findById(ligaId)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada"));

        return equipoRepository.findByLigaOrderByPuntosTotalesDesc(liga);
    }

    // ============================
    // OBTENER EQUIPO SEGURO
    // ============================

    public Equipo obtenerEquipoSeguro(Long id, String username) {

        return equipoRepository
                .findByIdAndLigaUsuarioUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado"));
    }

    // ============================
    // ELIMINAR EQUIPO
    // ============================

    public void eliminarEquipo(Long id, String username) {

        Equipo equipo = obtenerEquipoSeguro(id, username);

        equipoRepository.delete(equipo);
    }

    // ============================
    // FICHAR JUGADOR
    // ============================

    @Transactional
    public void ficharJugador(Long equipoId, Long jugadorId, String username) {

        Equipo equipo = obtenerEquipoSeguro(equipoId, username);

        Jugador jugador = jugadorRepository
                .findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        if (jugador.getEquipo() != null) {
            throw new IllegalStateException("El jugador ya pertenece a un equipo");
        }

        if (equipo.getPresupuesto() < jugador.getPrecio()) {
            throw new IllegalStateException("Presupuesto insuficiente");
        }

        equipo.setPresupuesto(equipo.getPresupuesto() - jugador.getPrecio());
        jugador.setEquipo(equipo);

        equipoRepository.save(equipo);
        jugadorRepository.save(jugador);
    }

    // ============================
    // VENDER JUGADOR
    // ============================

    @Transactional
    public void venderJugador(Long equipoId, Long jugadorId, String username) {

        Equipo equipo = obtenerEquipoSeguro(equipoId, username);

        Jugador jugador = jugadorRepository
                .findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        if (!jugador.getEquipo().getId().equals(equipoId)) {
            throw new IllegalStateException("El jugador no pertenece a tu equipo");
        }

        equipo.setPresupuesto(equipo.getPresupuesto() + jugador.getPrecio());
        jugador.setEquipo(null);
        jugador.setTitular(false);

        equipoRepository.save(equipo);
        jugadorRepository.save(jugador);
    }

    // ============================
    // JUGADORES DEL EQUIPO
    // ============================

    public List<Jugador> jugadoresEquipo(Long equipoId) {

        return jugadorRepository.findByEquipoId(equipoId);
    }

    // ============================
    // PONER TITULAR
    // ============================

    public void ponerTitular(Long jugadorId) {

        Jugador jugador =
                jugadorRepository.findById(jugadorId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Jugador no encontrado"));

        jugador.setTitular(true);

        jugadorRepository.save(jugador);
    }

    // ============================
    // ALINEAR TITULARES
    // ============================

    @Transactional
    public void alinearJugador(Long equipoId, Long jugadorId, String username) {

        Equipo equipo = obtenerEquipoSeguro(equipoId, username);

        Jugador jugador = jugadorRepository
                .findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        if (!jugador.getEquipo().getId().equals(equipoId)) {
            throw new IllegalStateException("El jugador no pertenece al equipo");
        }

        long titulares = jugadorRepository.countByEquipoIdAndTitularTrue(equipoId);

        if (!jugador.isTitular() && titulares >= 5) {
            throw new IllegalStateException("Solo puedes tener 5 titulares");
        }

        jugador.setTitular(!jugador.isTitular());

        jugadorRepository.save(jugador);
    }
}
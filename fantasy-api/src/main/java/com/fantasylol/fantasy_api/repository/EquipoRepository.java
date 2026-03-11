package com.fantasylol.fantasy_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.model.Usuario;


public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // ==========================
    // EQUIPOS DEL USUARIO
    // ==========================
    List<Equipo> findByLigaUsuarioUsername(String username);

    // ==========================
    // EQUIPO SEGURO
    // ==========================
    Optional<Equipo> findByIdAndLigaUsuarioUsername(Long id, String username);

    // ==========================
    // RANKING
    // ==========================
    List<Equipo> findByLigaOrderByPuntosTotalesDesc(Liga liga);

    List<Equipo> findByLigaIdOrderByPuntosTotalesDesc(Long ligaId);

    // ==========================
    // EQUIPO DEL USUARIO EN LIGA
    // ==========================
    Optional<Equipo> findByLigaIdAndUsuarioUsername(Long ligaId, String username);

    Optional<Equipo> findByUsuario(Usuario usuario);

    List<Equipo> findByLigaId(Long ligaId);

    Optional<Equipo> findByUsuarioUsername(String username);

}
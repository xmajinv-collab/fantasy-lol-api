package com.fantasylol.fantasy_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.model.Usuario;

public interface LigaRepository extends JpaRepository<Liga, Long> {

    // ======================================================
    // SEGURIDAD POR OWNERSHIP
    // ======================================================

    List<Liga> findByUsuario(Usuario usuario);
    
    List<Liga> findByUsuarioUsername(String username);

    Optional<Liga> findByIdAndUsuario(Long id, Usuario usuario);

    Optional<Liga> findByIdAndUsuarioUsername(Long id, String username);

    Optional<Liga> findByCodigoInvitacion(String codigo);

}
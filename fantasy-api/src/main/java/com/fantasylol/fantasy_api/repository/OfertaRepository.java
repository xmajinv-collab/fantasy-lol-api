package com.fantasylol.fantasy_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantasylol.fantasy_api.model.Oferta;

public interface OfertaRepository extends JpaRepository<Oferta,Long>{

    List<Oferta> findByEquipoLigaUsuarioUsername(String username);

    List<Oferta> findByEquipoIdAndEquipoLigaUsuarioUsername(Long equipoId,String username);

    Optional<Oferta> findByIdAndEquipoLigaUsuarioUsername(Long id,String username);

}
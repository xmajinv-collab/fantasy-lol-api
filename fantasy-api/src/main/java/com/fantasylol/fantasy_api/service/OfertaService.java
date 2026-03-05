package com.fantasylol.fantasy_api.service;

import org.springframework.stereotype.Service;

import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Oferta;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.OfertaRepository;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final JugadorRepository jugadorRepository;
    private final UsuarioRepository usuarioRepository;

    public OfertaService(
            OfertaRepository ofertaRepository,
            JugadorRepository jugadorRepository,
            UsuarioRepository usuarioRepository){

        this.ofertaRepository = ofertaRepository;
        this.jugadorRepository = jugadorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Oferta crearOferta(Long jugadorId,double cantidad,String username){

        Jugador jugador = jugadorRepository
                .findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Oferta oferta = new Oferta();
        oferta.setCantidad(cantidad);
        oferta.setJugador(jugador);
        oferta.setUsuario(usuario);

        return ofertaRepository.save(oferta);
    }
}
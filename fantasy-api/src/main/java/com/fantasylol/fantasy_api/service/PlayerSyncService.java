package com.fantasylol.fantasy_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

@Service
public class PlayerSyncService {

    private final JugadorRepository jugadorRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String API_KEY = "TU_API_KEY";

    public PlayerSyncService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public void sincronizarJugadores() {

        String url =
                "https://api.pandascore.co/lol/players?filter[league_id]=4197&token="
                        + API_KEY;

        List<?> jugadores = restTemplate.getForObject(url, List.class);

        for (Object obj : jugadores) {

            var map = (java.util.Map<?, ?>) obj;

            Long pandaId = Long.valueOf(map.get("id").toString());

            String nombre = map.get("name").toString();

            boolean existe = jugadorRepository
                    .findAll()
                    .stream()
                    .anyMatch(j -> j.getPandaScoreId() != null &&
                            j.getPandaScoreId().equals(pandaId));

            if (!existe) {

                Jugador jugador = new Jugador();

                jugador.setNombre(nombre);
                jugador.setPrecio(2000000);
                jugador.setPandaScoreId(pandaId);
                jugador.setTitular(false);
                jugador.setEnMercado(false);

                jugadorRepository.save(jugador);
            }

        }

    }
}
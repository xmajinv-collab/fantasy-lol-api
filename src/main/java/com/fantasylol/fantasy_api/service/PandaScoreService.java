package com.fantasylol.fantasy_api.service;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.repository.JugadorRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PandaScoreService {

    @Value("${pandascore.api.key}")
    private String apiKey;

    @Value("${pandascore.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final JugadorRepository jugadorRepository;

    public PandaScoreService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    // ==============================
    // IMPORTAR JUGADORES DESDE API
    // ==============================

    public void importarJugadores() {

        String url = baseUrl +
                "/lol/players?per_page=50&token=" +
                apiKey;

        List<Map<String, Object>> jugadores =
                restTemplate.getForObject(url, List.class);

        for (Map<String, Object> data : jugadores) {

            String nombre = (String) data.get("name");

            if (nombre == null) continue;

            Jugador jugador = new Jugador();

            jugador.setNombre(nombre);
            jugador.setValor(1000000);
            jugador.setPuntosTotales(0);
            jugador.setEnMercado(true);
            
            

            jugadorRepository.save(jugador);
        }
    }

}
package com.fantasylol.fantasy_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fantasylol.fantasy_api.model.Jugador;

@Service
public class PandaScoreService {

    @Value("${pandascore.api.key}")
    private String apiKey;

    @Value("${pandascore.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Jugador> obtenerJugadoresLEC() {

        String url = baseUrl + "/lol/players?filter[league]=lec&token=" + apiKey;

        Object[] response = restTemplate.getForObject(url, Object[].class);

        List<Jugador> jugadores = new ArrayList<>();

        if (response != null) {

            for (Object obj : response) {

                Jugador jugador = new Jugador();
                jugador.setNombre("Jugador LEC");
                jugador.setPrecio(1000000);
                jugador.setTitular(false);
                jugador.setEnMercado(true);

                jugadores.add(jugador);
            }
        }

        return jugadores;
    }
}
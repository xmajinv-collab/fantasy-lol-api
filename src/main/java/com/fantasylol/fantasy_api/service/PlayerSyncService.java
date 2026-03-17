package com.fantasylol.fantasy_api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fantasylol.fantasy_api.model.Jugador;
import com.fantasylol.fantasy_api.model.Liga;
import com.fantasylol.fantasy_api.repository.JugadorRepository;
import com.fantasylol.fantasy_api.repository.LigaRepository;

@Service
public class PlayerSyncService {

    private final JugadorRepository jugadorRepository;
    private final LigaRepository ligaRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${pandascore.api.key}")
    private String apiKey;

    public PlayerSyncService(
            JugadorRepository jugadorRepository,
            LigaRepository ligaRepository) {

        this.jugadorRepository = jugadorRepository;
        this.ligaRepository = ligaRepository;
    }

    // =============================
    // SINCRONIZAR JUGADORES
    // =============================

    public void sincronizarJugadores(Long ligaId) {

        // 🔥 Obtener liga
        Liga liga = ligaRepository.findById(ligaId)
                .orElseThrow(() -> new RuntimeException("Liga no encontrada"));

        // 🔥 URL correcta
        String url = "https://api.pandascore.co/lol/players?token=" + apiKey;

        // 🔥 Llamada API
        List<Map<String, Object>> jugadores =
                (List<Map<String, Object>>) (List<?>)
                        restTemplate.getForObject(url, List.class);

        if (jugadores == null) {
            throw new RuntimeException("No se pudieron obtener los jugadores de la API");
        }

        System.out.println("Jugadores recibidos: " + jugadores.size());

        // 🔥 Cache de jugadores existentes (optimización)
        List<Jugador> existentes = jugadorRepository.findByLigaId(ligaId);

        // 🔥 Recorrer jugadores
        for (Map<String, Object> map : jugadores) {

            // Validar ID
            if (map.get("id") == null) continue;

            Long pandaId = Long.valueOf(map.get("id").toString());

            // 🔥 Nombre seguro
            String nombre;

            if (map.get("name") != null) {
                nombre = map.get("name").toString();
            } else if (map.get("slug") != null) {
                nombre = map.get("slug").toString();
            } else {
                continue; // saltar si no tiene nombre válido
            }

            // 🔥 Comprobar si ya existe
            boolean existe = existentes.stream().anyMatch(j ->
                    j.getPandaScoreId() != null &&
                    j.getPandaScoreId().equals(pandaId)
            );

            if (!existe) {

                Jugador jugador = new Jugador();

                jugador.setNombre(nombre);
                jugador.setPrecio(2000000);
                jugador.setPandaScoreId(pandaId);

                // 🔥 MUY IMPORTANTE
                jugador.setLiga(liga);
                jugador.setEnMercado(true);
                jugador.setTitular(false);

                jugadorRepository.save(jugador);
            }
        }

        System.out.println("✔ Jugadores sincronizados correctamente");
    }
}
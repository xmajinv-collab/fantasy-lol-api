package com.fantasylol.fantasy_api.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PandaScoreService {

    @Value("${pandascore.api.key}")
    private String apiKey;

    @Value("${pandascore.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerPartidosLEC() {

        String url = baseUrl +
                "/lol/matches?filter[league_id]=4197&token=" +
                apiKey;

        return restTemplate.getForObject(url, String.class);
    }
}
package com.fantasylol.fantasy_api.service;

import org.springframework.stereotype.Service;

@Service
public class FantasyPointsService {

    public int calcularPuntos(
            int kills,
            int assists,
            int deaths,
            boolean victoria,
            boolean mvp,
            boolean pentakill,
            boolean firstBlood
    ) {

        int puntos = 0;

        puntos += kills * 3;
        puntos += assists * 2;
        puntos -= deaths;

        if (victoria) puntos += 5;
        else puntos -= 2;

        if (mvp) puntos += 7;

        if (pentakill) puntos += 10;

        if (firstBlood) puntos += 4;

        return puntos;
    }
}
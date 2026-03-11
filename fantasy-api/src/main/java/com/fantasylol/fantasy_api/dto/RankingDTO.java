package com.fantasylol.fantasy_api.dto;

public class RankingDTO {

    private String equipo;
    private int puntos;

    public RankingDTO(String equipo, int puntos) {
        this.equipo = equipo;
        this.puntos = puntos;
    }

    public String getEquipo() {
        return equipo;
    }

    public int getPuntos() {
        return puntos;
    }
}
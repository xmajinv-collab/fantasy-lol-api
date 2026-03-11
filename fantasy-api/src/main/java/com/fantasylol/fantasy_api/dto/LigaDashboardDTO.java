package com.fantasylol.fantasy_api.dto;

import java.util.List;

import com.fantasylol.fantasy_api.model.Equipo;
import com.fantasylol.fantasy_api.model.Jugador;

public class LigaDashboardDTO {

    private String nombreLiga;
    private List<RankingDTO> ranking;
    private List<Equipo> equipos;
    private List<Jugador> mercado;

    public LigaDashboardDTO(
            String nombreLiga,
            List<RankingDTO> ranking,
            List<Equipo> equipos,
            List<Jugador> mercado) {

        this.nombreLiga = nombreLiga;
        this.ranking = ranking;
        this.equipos = equipos;
        this.mercado = mercado;

    }

    public String getNombreLiga() {
        return nombreLiga;
    }

    public List<RankingDTO> getRanking() {
        return ranking;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Jugador> getMercado() {
        return mercado;
    }

}
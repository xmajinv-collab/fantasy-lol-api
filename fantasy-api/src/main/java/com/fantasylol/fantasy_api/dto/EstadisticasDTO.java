package com.fantasylol.fantasy_api.dto;

public class EstadisticasDTO {

    private int kills;
    private int assists;
    private int deaths;

    public EstadisticasDTO(){}

    public EstadisticasDTO(int kills, int assists, int deaths){
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
    }

    public int getKills(){
        return kills;
    }

    public int getAssists(){
        return assists;
    }

    public int getDeaths(){
        return deaths;
    }

    public void setKills(int kills){
        this.kills = kills;
    }

    public void setAssists(int assists){
        this.assists = assists;
    }

    public void setDeaths(int deaths){
        this.deaths = deaths;
    }

}
package com.fantasylol.fantasy_api.dto;

public class RankingDTO {

    private int posicion;
    private String nombreEquipo;
    private int puntosTotales;
    private double presupuesto;
    private int totalJugadores;

    public RankingDTO(int posicion, String nombreEquipo, int puntosTotales, double presupuesto, int totalJugadores) {
        this.posicion = posicion;
        this.nombreEquipo = nombreEquipo;
        this.puntosTotales = puntosTotales;
        this.presupuesto = presupuesto;
        this.totalJugadores = totalJugadores;
    }

    public int getPosicion() {
        return posicion;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public int getTotalJugadores() {
        return totalJugadores;
    }
}
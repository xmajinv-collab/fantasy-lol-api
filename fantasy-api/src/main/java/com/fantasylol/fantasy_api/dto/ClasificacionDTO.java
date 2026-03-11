package com.fantasylol.fantasy_api.dto;

public class ClasificacionDTO {

    private int posicion;
    private String nombreEquipo;
    private int puntos;
    private int diferenciaConLider;

    public ClasificacionDTO(int posicion, String nombreEquipo, int puntos, int diferenciaConLider) {
        this.posicion = posicion;
        this.nombreEquipo = nombreEquipo;
        this.puntos = puntos;
        this.diferenciaConLider = diferenciaConLider;
    }

    public int getPosicion() {
        return posicion;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getDiferenciaConLider() {
        return diferenciaConLider;
    }
}
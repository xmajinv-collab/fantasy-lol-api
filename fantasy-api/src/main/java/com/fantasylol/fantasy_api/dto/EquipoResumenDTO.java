package com.fantasylol.fantasy_api.dto;

public class EquipoResumenDTO {

    private Long id;
    private String nombre;
    private double presupuesto;
    private int puntos;

    public EquipoResumenDTO(Long id, String nombre, double presupuesto, int puntos) {
        this.id = id;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.puntos = puntos;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public int getPuntos() {
        return puntos;
    }
}
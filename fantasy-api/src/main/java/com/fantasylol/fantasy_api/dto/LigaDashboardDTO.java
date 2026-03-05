package com.fantasylol.fantasy_api.dto;

public class LigaDashboardDTO {

    private Long id;
    private String nombre;
    private boolean jornadaActiva;

    public LigaDashboardDTO(Long id, String nombre, boolean jornadaActiva){
        this.id = id;
        this.nombre = nombre;
        this.jornadaActiva = jornadaActiva;
    }

    public Long getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public boolean isJornadaActiva(){
        return jornadaActiva;
    }
}
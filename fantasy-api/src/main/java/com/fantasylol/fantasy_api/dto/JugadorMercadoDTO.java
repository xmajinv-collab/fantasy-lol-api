package com.fantasylol.fantasy_api.dto;

public class JugadorMercadoDTO {

    private Long id;
    private String nombre;
    private double precio;
    private Long pandaScoreId;

    public JugadorMercadoDTO(Long id, String nombre, double precio, Long pandaScoreId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.pandaScoreId = pandaScoreId;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Long getPandaScoreId() {
        return pandaScoreId;
    }
}
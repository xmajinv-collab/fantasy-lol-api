package com.fantasylol.fantasy_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private double precio;

    private boolean titular;

    private boolean enMercado;

    private Long pandaScoreId;

    private LocalDateTime fechaFinSubasta;

    private int getpuntosTotales = 0;
        

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liga_id")
    private Liga liga;

    public Jugador(){}

    public Jugador(String nombre,double precio,Long pandaScoreId){
        this.nombre = nombre;
        this.precio = precio;
        this.pandaScoreId = pandaScoreId;
        this.titular = false;
        this.enMercado = false;
    }

    public Long getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public double getPrecio(){
        return precio;
    }

    public boolean isTitular(){
        return titular;
    }

    public boolean isEnMercado(){
        return enMercado;
    }

    public Long getPandaScoreId(){
        return pandaScoreId;
    }

    public LocalDateTime getFechaFinSubasta(){
        return fechaFinSubasta;
    }

    public Equipo getEquipo(){
        return equipo;
    }

    public Liga getLiga(){
        return liga;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public void setTitular(boolean titular){
        this.titular = titular;
    }

    public void setEnMercado(boolean enMercado){
        this.enMercado = enMercado;
    }

    public void setPandaScoreId(Long pandaScoreId){
        this.pandaScoreId = pandaScoreId;
    }

    public void setFechaFinSubasta(LocalDateTime fechaFinSubasta){
        this.fechaFinSubasta = fechaFinSubasta;
    }

    public void setEquipo(Equipo equipo){
        this.equipo = equipo;
    }

    public void setLiga(Liga liga){
        this.liga = liga;
    }
    public int getPuntosTotales() {
       return getpuntosTotales;
    }
    public void setPuntosTotales(int puntosTotales) {
        this.getpuntosTotales = puntosTotales;
    }
}
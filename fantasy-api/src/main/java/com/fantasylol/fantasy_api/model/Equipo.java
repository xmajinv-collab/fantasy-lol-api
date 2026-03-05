package com.fantasylol.fantasy_api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private double presupuesto;

    private int puntosTotales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Jugador> jugadores = new ArrayList<>();

    // ======================
    // CONSTRUCTORES
    // ======================

    public Equipo() {}

    public Equipo(String nombre, double presupuesto) {
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.puntosTotales = 0;
    }

    // ======================
    // GETTERS
    // ======================

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public Liga getLiga() {
        return liga;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    // ======================
    // SETTERS
    // ======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
}
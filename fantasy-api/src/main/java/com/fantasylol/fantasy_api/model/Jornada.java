package com.fantasylol.fantasy_api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jornadas")
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private boolean activa;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    private Liga liga;

    @OneToMany(mappedBy = "jornada")
    private List<PuntuacionJornada> puntuaciones;

    public Jornada() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public List<PuntuacionJornada> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(List<PuntuacionJornada> puntuaciones) {
        this.puntuaciones = puntuaciones;
    }
}
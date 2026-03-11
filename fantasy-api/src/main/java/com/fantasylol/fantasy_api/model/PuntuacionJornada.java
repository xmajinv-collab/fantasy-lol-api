package com.fantasylol.fantasy_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "puntuaciones_jornada")
public class PuntuacionJornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int kills;
    private int assists;
    private int deaths;
    private int puntos;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "jornada_id")
    private Jornada jornada;

    public PuntuacionJornada() {
    }

    public PuntuacionJornada(Jugador jugador,
                             Jornada jornada,
                             int kills,
                             int assists,
                             int deaths,
                             int puntos) {
        this.jugador = jugador;
        this.jornada = jornada;
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
        this.puntos = puntos;
    }

    public Long getId() {
        return id;
    }

    public int getKills() {
        return kills;
    }

    public int getAssists() {
        return assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getPuntos() {
        return puntos;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}
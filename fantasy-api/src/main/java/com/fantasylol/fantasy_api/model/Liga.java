package com.fantasylol.fantasy_api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "ligas")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private boolean jornadaActiva;

    private String codigoInvitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "liga", cascade = CascadeType.ALL)
    private List<Equipo> equipos;

    // ======================
    // CONSTRUCTORES
    // ======================

    public Liga() {}

    public Liga(String nombre) {
        this.nombre = nombre;
        this.jornadaActiva = false;
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

    public boolean isJornadaActiva() {
        return jornadaActiva;
    }

    public String getCodigoInvitacion() {
        return codigoInvitacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Equipo> getEquipos() {
        return equipos;
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

    public void setJornadaActiva(boolean jornadaActiva) {
        this.jornadaActiva = jornadaActiva;
    }

    public void setCodigoInvitacion(String codigoInvitacion) {
        this.codigoInvitacion = codigoInvitacion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}
package com.fantasylol.fantasy_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double cantidad;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    public Oferta(){}

    public Long getId(){
        return id;
    }

    public double getCantidad(){
        return cantidad;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public Jugador getJugador(){
        return jugador;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setCantidad(double cantidad){
        this.cantidad = cantidad;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}

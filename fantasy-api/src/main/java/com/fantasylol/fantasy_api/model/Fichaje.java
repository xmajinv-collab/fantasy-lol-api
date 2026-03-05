package com.fantasylol.fantasy_api.model;

import jakarta.persistence.*;

@Entity
public class Fichaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    public Fichaje(){}

    public Fichaje(String descripcion){
        this.descripcion = descripcion;
    }

    public Long getId(){
        return id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

}
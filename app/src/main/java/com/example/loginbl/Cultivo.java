package com.example.loginbl;

import com.google.firebase.Timestamp;

public class Cultivo {
    private String id;
    private String nombre;
    private int cantidad;
    private Timestamp fecha;

    public Cultivo() {
    }
    public Cultivo(String nombre, int cantidad, Timestamp fecha) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}

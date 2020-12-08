package com.example.appproyectofinal;

public class Datos {
    String fecha;
    String estado;

    public Datos(String fecha, String dato) {
        this.fecha = fecha;
        this.estado = dato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String dato) {
        this.estado = dato;
    }
}

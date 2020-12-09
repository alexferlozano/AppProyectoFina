package com.example.appproyectofinal;

public class Datos {
    String value;
    String created_at;

    public Datos(String fecha, String dato) {
        this.value = dato;
        this.created_at = fecha;
    }

    public String getFecha() {
        return value;
    }

    public void setFecha(String fecha) {
        this.value = fecha;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String dato) {
        this.created_at = dato;
    }
}

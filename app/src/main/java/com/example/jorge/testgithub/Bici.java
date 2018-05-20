package com.example.jorge.testgithub;

public class Bici {
    private int id = 0;
    private String estado = "";
    private Parada parada = null;

    public Bici() {
    }

    public Bici(int id, String estado, Parada parada) {
        this.id = id;
        this.estado = estado;
        this.parada = parada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }
}

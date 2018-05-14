package com.example.jorge.testgithub;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Bici> cargarBicis() {
        // TODO: Cargar Bicicletas desde la bd
        List<Bici> bicis = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            bicis.add(new Bici(i, "Disponible", new Parada(i, "Centro", "-232332223 324354524", "18 de Julio  y Montevideo", 20, 20)));
            bicis.add(new Bici(i, "Ocupada", new Parada(i + 5, "Centro", "-232332223 324354524", "18 de Julio  y Montevideo", 20, 20)));
        }

        return bicis;
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

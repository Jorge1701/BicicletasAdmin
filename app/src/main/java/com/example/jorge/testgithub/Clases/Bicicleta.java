package com.example.jorge.testgithub.Clases;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaBicicletas;
import com.example.jorge.testgithub.BD.RespuestaParadas;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bicicleta {
    private String id = "";
    private String disponible = "";
    private String parada = "";
    private String fechaAlquilada = "20-6-2018";

    public Bicicleta() {
    }

    public Bicicleta(String id, String disponible, String parada) {
        this.id = id;
        this.disponible = disponible;
        this.parada = parada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getParada() {
        return parada;
    }

    public void setParada(String parada) {
        this.parada = parada;
    }

    public String getFechaAlquilada() {
        return fechaAlquilada;
    }

    public void setFechaAlquilada(String fechaAlquilada) {
        this.fechaAlquilada = fechaAlquilada;
    }
}

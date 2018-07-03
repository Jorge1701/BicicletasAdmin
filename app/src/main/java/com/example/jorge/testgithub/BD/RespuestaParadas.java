package com.example.jorge.testgithub.BD;

import com.example.jorge.testgithub.Clases.Parada;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaParadas {

    @SerializedName("codigo")
    private String codigo;
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("paradas")
    private List<Parada> paradas;
    @SerializedName("promedios")
    private List<Parada> promedios;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas;
    }

    public List<Parada> getPromedios() {
        return promedios;
    }

    public void setPromedios(List<Parada> promedios) {
        this.promedios = promedios;
    }
}

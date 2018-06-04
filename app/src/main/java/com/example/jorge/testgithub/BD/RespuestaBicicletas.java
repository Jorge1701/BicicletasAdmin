package com.example.jorge.testgithub.BD;

import com.example.jorge.testgithub.Clases.Bicicleta;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaBicicletas {

    @SerializedName("codigo")
    private String codigo;
    @SerializedName ("mensaje")
    private String mensaje;
    @SerializedName ("bicicletas")
    private List<Bicicleta> bicicletas;

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

    public List<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(List<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }
}

package com.example.jorge.testgithub.Clases;

import android.util.Log;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.BD.RespuestaParadas;
import com.example.jorge.testgithub.Util.Paradas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Parada {
    private int id;
    private String nombre;
    private double latitud;
    private double longitud;
    private String direccion;
    private int cantBicis;
    private int cantidadLibre;
    private int cantidadOcupada;

    public static void agregarParada(final Paradas p ,String nombre,String direccion,double lat,double lng,int cantBicis){
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<Respuesta> call = bd.agregarParada(nombre,lat,lng,direccion,cantBicis);
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                p.agregarParada(response.body().getCodigo().equals("1") ? true : false);
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                p.agregarParada(false);
            }
        });
    }

    public int getCantidadLibre() {
        return cantidadLibre;
    }

    public void setCantidadLibre(int cantidadLibre) {
        this.cantidadLibre = cantidadLibre;
    }

    public int getCantidadOcupada() {
        return cantidadOcupada;
    }

    public void setCantidadOcupada(int cantidadOcupada) {
        this.cantidadOcupada = cantidadOcupada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLat() {
        return latitud;
    }

    public void setLat(double lat) {
        this.latitud = lat;
    }

    public double getLng() {
        return longitud;
    }

    public void setLng(double lng) {
        this.longitud = lng;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCantBicis() {
        return cantBicis;
    }

    public void setCantBicis(int cantBicis) {
        this.cantBicis = cantBicis;
    }
}

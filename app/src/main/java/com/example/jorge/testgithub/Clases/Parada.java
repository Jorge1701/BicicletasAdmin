package com.example.jorge.testgithub.Clases;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.Util.Paradas;

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
    private int cantAlquileresDia;
    private int cantAlquileresSemana;
    private int cantAlquileresMes;

    public Parada(){
        this.cantAlquileresDia = 5;
        this.cantAlquileresSemana = 7;
        this.cantAlquileresMes = 10;
    }

    public Parada(int id, String nombre, double latitud, double longitud, String direccion, int cantBicis, int cantidadLibre, int cantidadOcupada) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.cantBicis = cantBicis;
        this.cantidadLibre = cantidadLibre;
        this.cantidadOcupada = cantidadOcupada;
    }

    public static void agregarParada(final Paradas p, String nombre, String direccion, double lat, double lng, int cantBicis) {
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<Respuesta> call = bd.agregarParada(nombre, lat, lng, direccion, cantBicis);
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

    public static void editarParada(final Paradas p, int id, String nombre, String direccion, double lat, double lng, int cantBicis) {
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<Respuesta> call = bd.editarParada(id, nombre, lat, lng, direccion, cantBicis);
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                p.editarParada(response.body().getCodigo().equals("1") ? true : false);
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                p.editarParada(false);
            }
        });
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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

    public int getCantAlquileresDia() {
        return cantAlquileresDia;
    }

    public void setCantAlquileresDia(int cantAlquileresDia) {
        this.cantAlquileresDia = cantAlquileresDia;
    }

    public int getCantAlquileresSemana() {
        return cantAlquileresSemana;
    }

    public void setCantAlquileresSemana(int cantAlquileresSemana) {
        this.cantAlquileresSemana = cantAlquileresSemana;
    }

    public int getCantAlquileresMes() {
        return cantAlquileresMes;
    }

    public void setCantAlquileresMes(int cantAlquileresMes) {
        this.cantAlquileresMes = cantAlquileresMes;
    }
}

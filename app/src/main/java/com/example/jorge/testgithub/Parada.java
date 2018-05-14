package com.example.jorge.testgithub;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Parada{

    int numero = 0;
    String nombre = "";
    String ubicacion = "";
    String direccion = "";

    int cantBicis = 0;
    int cantBicisOcupadas = 0;
    int cantBicisLibres = 0;
    String estado = "";

    public Parada (){
    }

    public Parada(int numero, String nombre, String ubicacion, String direccion, int cantBicis, int cantBicisOcupadas) {
        this.numero = numero;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.direccion = direccion;
        this.cantBicis = cantBicis;
        this.cantBicisOcupadas = cantBicisOcupadas;
        this.cantBicisLibres = cantBicis - cantBicisOcupadas;
        if(cantBicisOcupadas < cantBicis){
            this.estado = "Libre";
        }else{
            this.estado = "Vacía";
        }



    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
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

    public int getCantBicisOcupadas() {
        return cantBicisOcupadas;
    }

    public void setCantBicisOcupadas(int cantBicisOcupadas) {
        this.cantBicisOcupadas = cantBicisOcupadas;
    }

    public int getCantBicisLibres (){
        return cantBicisLibres;
    }
}

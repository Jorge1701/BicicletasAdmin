package com.example.jorge.testgithub;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgregarParada extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    @BindView(R.id.nombre)
    EditText nombre;
    @BindView(R.id.cantBicis)
    EditText cantBicis;
    @BindView(R.id.agregarParada)
    Button agregarParada;
    @BindView(R.id.nombreError)
    TextInputLayout nombreError;
    @BindView(R.id.cantBicisError)
    TextInputLayout cantBicisError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_parada);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcador = null;

        //obtener la posicion de todas las paradas y hacer los marcadores
        LatLng paysandu = new LatLng(-32.316465, -58.088980);
        mMap.addMarker(new MarkerOptions().position(new LatLng(-32.317921, -58.089010)).title("Parada1").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paysandu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                cantBicisError.setError(null);
                nombreError.setError(null);
                return true;
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub

                cantBicisError.setError(null);
                nombreError.setError(null);

                if (marcador == null) {
                    Toast.makeText(AgregarParada.this, "Direccion:" + getDireccion(arg0), Toast.LENGTH_LONG).show();
                    Log.d("Direccion:", getDireccion(arg0));
                    marcador = mMap.addMarker(new MarkerOptions().position(arg0).title(getDireccion(arg0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arg0));

                } else {
                    marcador.remove();
                    Toast.makeText(AgregarParada.this, "Direccion:" + getDireccion(arg0), Toast.LENGTH_LONG).show();
                    Log.d("Direccion", getDireccion(arg0));
                    marcador = mMap.addMarker(new MarkerOptions().position(arg0).title(getDireccion(arg0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arg0));
                }

            }
        });

    }

    public String getDireccion(LatLng posicion) {
        Geocoder gc = new Geocoder(AgregarParada.this);
        List<Address> list = null;
        try {
            list = gc.getFromLocation(posicion.latitude, posicion.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        Address add = list.get(0);
        return add.getAddressLine(0);
    }


    public void AgregarParada(View v) {
        /*Agregar Parada: Seleccionar Ubicación en el mapa, dirección, nombre y cantidad de
        bicis.*/

        nombreError.setError(null);
        cantBicisError.setError(null);

        //chequeo que no deje nada vacio o incorrecto
        if (nombre.getText().toString().trim().equals("")) {
            nombreError.setError("Nombre Invalido");
            return;
        } else if (cantBicis.getText().toString().trim().equals("") || Integer.valueOf(cantBicis.getText().toString().trim()) < 1) {
            cantBicisError.setError("Cantidad Invalida");
            return;

        }

        //chequeo que no exista una parada con ese nombre

        //llamo al ingresar parada

        //Toast.makeText(this, "Nombre:" + parada, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Ubicacion:" + marcador.getPosition().latitude + "|" + marcador.getPosition().longitude, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Direccion:" + marcador.getTitle(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "CantBicis:" + bi, Toast.LENGTH_LONG).show();

    }
}

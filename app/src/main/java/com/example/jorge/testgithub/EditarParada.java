package com.example.jorge.testgithub;

import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditarParada extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    @BindView(R.id.nombre)
    EditText nombre;
    @BindView(R.id.cantBicis)
    EditText cantBicis;
    @BindView(R.id.editarParada)
    Button editarParada;
    @BindView(R.id.nombreError)
    TextInputLayout nombreError;
    @BindView(R.id.cantBicisError)
    TextInputLayout cantBicisError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_parada);
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(-32.315908, -58.084246)).title("Parada2").draggable(true));
        mMap.addMarker(new MarkerOptions().position(new LatLng(-32.314838, -58.088559)).title("Parada3").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paysandu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        //mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                               @Override
                                               public boolean onMarkerClick(Marker marker) {
                                                   marcador = marker;

                                                   nombre.setVisibility(View.VISIBLE);
                                                   cantBicis.setVisibility(View.VISIBLE);
                                                   editarParada.setVisibility(View.VISIBLE);
                                                   nombreError.setVisibility(View.VISIBLE);
                                                   cantBicisError.setVisibility(View.VISIBLE);

                                                   cantBicisError.setError(null);
                                                   nombreError.setError(null);

                                                   if (marker.getTitle().equals("Parada1")) {
                                                       nombre.setText("Parada1");
                                                       cantBicis.setText(String.valueOf(1));
                                                   }

                                                   if (marker.getTitle().equals("Parada2")) {
                                                       nombre.setText("Parada2");
                                                       cantBicis.setText(String.valueOf(2));
                                                   }

                                                   if (marker.getTitle().equals("Parada3")) {
                                                       nombre.setText("Parada3");
                                                       cantBicis.setText(String.valueOf(3));
                                                   }

                                                   marker.showInfoWindow();

                                                   return true;
                                               }
                                           }

        );

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //Toast.makeText(EditarParada.this, "TERMINO DE MOVER", Toast.LENGTH_LONG).show();
                nombre.setText(marker.getTitle());
                marcador = marker;
                nombre.setVisibility(View.VISIBLE);
                cantBicis.setVisibility(View.VISIBLE);
                editarParada.setVisibility(View.VISIBLE);
                nombreError.setVisibility(View.VISIBLE);
                cantBicisError.setVisibility(View.VISIBLE);
                cantBicisError.setError(null);
                nombreError.setError(null);
            }
        });

    }

    public String getDireccion(LatLng posicion) {
        Geocoder gc = new Geocoder(EditarParada.this);
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

    public void EditarParada(View v) {

        nombreError.setError(null);
        cantBicisError.setError(null);

        if (nombre.getText().toString().trim().equals("")) {
            nombreError.setError("Nombre Invalido");
            return;
        }else if (cantBicis.getText().toString().trim().equals("") || Integer.valueOf(cantBicis.getText().toString().trim()) < 1) {
            cantBicisError.setError("Cantidad Invalida");
            return;

        }

        Toast.makeText(EditarParada.this,"TODO BIEN",Toast.LENGTH_LONG).show();
        Toast.makeText(EditarParada.this, "NOMBRE:" + nombre.getText().toString().trim(), Toast.LENGTH_LONG).show();
        Toast.makeText(EditarParada.this, "Ubicacion" + marcador.getPosition().latitude + "|" + marcador.getPosition().longitude, Toast.LENGTH_LONG).show();
        Toast.makeText(EditarParada.this, "DIRECCION:" + getDireccion(marcador.getPosition()), Toast.LENGTH_LONG).show();
        Toast.makeText(EditarParada.this, "BICIS:" + cantBicis.getText().toString().trim(), Toast.LENGTH_LONG).show();
    }
}

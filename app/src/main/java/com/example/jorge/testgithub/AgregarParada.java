package com.example.jorge.testgithub;


import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.BD.RespuestaParadas;
import com.example.jorge.testgithub.Util.Paradas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.jorge.testgithub.Clases.Parada;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarParada extends Fragment implements OnMapReadyCallback, Paradas {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker marcador;
    private List<Marker> marcadores;
    public List<MarkerOptions> paradas;
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


    public AgregarParada() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(paradas == null)
            paradas = new ArrayList<>();

        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaParadas> call = bd.getParadas();
        call.enqueue(new Callback<RespuestaParadas>() {
            @Override
            public void onResponse(Call<RespuestaParadas> call, Response<RespuestaParadas> response) {

                if(response.isSuccessful()){
                    List<Parada> re = response.body().getParadas();
                    //List<MarkerOptions> paradas = new ArrayList<>();
                    for(int i=0; i < re.size();i++) {
                        paradas.add(new MarkerOptions().position(new LatLng(re.get(i).getLatitud(), re.get(i).getLongitud())).title(re.get(i).getNombre()));
                    }
                }

            }

            @Override
            public void onFailure(Call<RespuestaParadas> call, Throwable t) {

            }
        });
        //
        View v = inflater.inflate(R.layout.fragment_agregar_parada, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        ButterKnife.bind(this, v);
        Button btn = (Button) v.findViewById(R.id.agregarParada);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreError.setError(null);
                cantBicisError.setError(null);

                //chequeo que no deje nada vacio o incorrecto
                if (nombre.getText().toString().trim().equals("")) {
                    nombreError.setError("Nombre Invalido");
                    return;
                } else if (cantBicis.getText().toString().trim().equals("") || Integer.valueOf(cantBicis.getText().toString().trim()) < 1) {
                    cantBicisError.setError("Cantidad Invalida");
                    return;

                } else if (marcador == null) {
                    Toast.makeText(getActivity(), "Seleccione un punto en el mapa", Toast.LENGTH_LONG).show();
                    return;
                }

                //chequeo que no exista una parada con ese nombre

                //llamo al ingresar parada
               /* BDInterface bd = BDCliente.getClient().create(BDInterface.class);
                Call<Respuesta> call = bd.agregarParada(nombre.getText().toString().trim(),marcador.getPosition().latitude,marcador.getPosition().longitude,marcador.getTitle(),Integer.valueOf(cantBicis.getText().toString().trim()));
                call.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        Toast.makeText(getActivity(), "PARADA AGREGADA", Toast.LENGTH_SHORT).show();
                        Log.d("ASD", "onResponse: PARADA AGREGADA");
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Log.d("ASD", t.getMessage().toString());
                        Toast.makeText(getActivity(), "ERROR AL AGREGAR", Toast.LENGTH_SHORT).show();
                    }
                });*/


                altaParada(nombre.getText().toString().trim(), marcador.getTitle(), marcador.getPosition().latitude, marcador.getPosition().longitude, Integer.valueOf(cantBicis.getText().toString().trim()));
                //Toast.makeText(getActivity(), "Nombre:" + nombre.getText().toString().trim() , Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "Ubicacion:" + marcador.getPosition().latitude + "|" + marcador.getPosition().longitude, Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "Direccion:" + marcador.getTitle(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "CantBicis:" + cantBicis.getText().toString().trim(), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    public void altaParada(String nombre, String direccion, double lat, double lng, int cantBicis) {
        Parada.agregarParada(this, nombre, direccion, lat, lng, cantBicis);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcador = null;

        Log.d("ASD", "onMapReady");
        //
        if(paradas != null){
            for(int i=0; i < paradas.size(); i++){
                mMap.addMarker(paradas.get(i));
            }
        }
        //obtener la posicion de todas las paradas y hacer los marcadores
        LatLng paysandu = new LatLng(-32.316465, -58.088980);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-32.317921, -58.089010)).title("Parada1").draggable(true));
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
                cantBicisError.setError(null);
                nombreError.setError(null);

                if (marcador == null) {
                    marcador = mMap.addMarker(new MarkerOptions().position(arg0).title(getDireccion(arg0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arg0));

                } else {
                    marcador.remove();
                    marcador = mMap.addMarker(new MarkerOptions().position(arg0).title(getDireccion(arg0)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arg0));
                }

            }
        });
    }

    public String getDireccion(LatLng posicion) {
        Geocoder gc = new Geocoder(getActivity());
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

    @Override
    public boolean agregarParada(boolean ok) {

        if(ok){
            Toast.makeText(getActivity(), "PARADA AGREGADA", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "ERROR AL AGREGAR", Toast.LENGTH_LONG).show();
        }

        return ok;
    }
}

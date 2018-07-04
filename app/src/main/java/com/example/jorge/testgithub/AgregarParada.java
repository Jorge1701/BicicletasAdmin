package com.example.jorge.testgithub;


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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaParadas;
import com.example.jorge.testgithub.Clases.Parada;
import com.example.jorge.testgithub.Util.Paradas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarParada extends Fragment implements OnMapReadyCallback, Paradas {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker marcador;
    public List<Parada> paradas;
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
    @BindView(R.id.carga)
    LinearLayout carga;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.pbAgregar)
    ProgressBar pbAgregar;


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
        View v = inflater.inflate(R.layout.fragment_agregar_parada, container, false);
        cargarParadas();
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
                    nombreError.setError("Nombre inválido");
                    return;
                } else if (cantBicis.getText().toString().trim().equals("") || Integer.valueOf(cantBicis.getText().toString().trim()) < 1) {
                    cantBicisError.setError("Cantidad inválida");
                    return;

                } else if (Integer.valueOf(cantBicis.getText().toString().trim()) > 20) {
                    cantBicisError.setError("Cantidad inválida (20 Max)");
                    return;
                } else if (marcador == null) {
                    Toast.makeText(getActivity(), "Seleccione un punto en el mapa", Toast.LENGTH_LONG).show();
                    return;
                }

                pbAgregar.setVisibility(View.VISIBLE);
                agregarParada.setVisibility(View.GONE);
                altaParada(nombre.getText().toString().trim(), marcador.getTitle(), marcador.getPosition().latitude, marcador.getPosition().longitude, Integer.valueOf(cantBicis.getText().toString().trim()));
            }
        });
        return v;
    }

    private void cargarParadas() {
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaParadas> call = bd.getParadas();
        call.enqueue(new Callback<RespuestaParadas>() {
            @Override
            public void onResponse(Call<RespuestaParadas> call, Response<RespuestaParadas> response) {
                paradas = new ArrayList<>();
                if (response.body().getCodigo().equals("1")) {
                    List<Parada> re = response.body().getParadas();
                    for (int i = 0; i < re.size(); i++)
                        paradas.add(re.get(i));
                }

                if (mMap != null)
                    prepararMapa();
            }

            @Override
            public void onFailure(Call<RespuestaParadas> call, Throwable t) {
                Toast.makeText(getActivity(), "Error de conexión con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void altaParada(String nombre, String direccion, double lat, double lng, int cantBicis) {
        Parada.agregarParada(this, nombre, direccion, lat, lng, cantBicis);
    }

    private List<Marker> marks;

    public void prepararMapa() {
        if (marks == null)
            marks = new ArrayList<>();

        for (Marker m : marks)
            m.remove();

        marks.clear();

        for (int i = 0; i < paradas.size(); i++) {
            marks.add(mMap.addMarker(new MarkerOptions().position(new LatLng(paradas.get(i).getLatitud(), paradas.get(i).getLongitud())).title(paradas.get(i).getNombre()).draggable(true).snippet(paradas.get(i).getDireccion())));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();

                cantBicisError.setError(null);
                nombreError.setError(null);
                return true;
            }
        });


        carga.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

        pbAgregar.setVisibility(View.GONE);
        agregarParada.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcador = null;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub


                cantBicisError.setError(null);
                nombreError.setError(null);

                if (marcador == null) {
                    marcador = mMap.addMarker(new MarkerOptions().position(arg0).title(getDireccion(arg0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arg0));

                } else {
                    marcador.remove();
                    marcador = mMap.addMarker(new MarkerOptions().position(arg0).title(getDireccion(arg0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(arg0));
                }

            }
        });

        if (paradas != null)
            prepararMapa();


        //obtener la posicion de todas las paradas y hacer los marcadores
        LatLng paysandu = new LatLng(-32.316465, -58.088980);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-32.317921, -58.089010)).title("Parada1").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paysandu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
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

        if (ok) {
            cargarParadas();
            Toast.makeText(getActivity(), "Parada agregada con éxito.", Toast.LENGTH_LONG).show();
            nombre.setText("");
            cantBicis.setText("");
            marcador.remove();
        } else {
            Toast.makeText(getActivity(), "Hubo un error al agregar la parada, reintente.", Toast.LENGTH_LONG).show();
            pbAgregar.setVisibility(View.GONE);
            agregarParada.setVisibility(View.VISIBLE);
        }



        return ok;
    }

    @Override
    public boolean editarParada(boolean ok) {
        return false;
    }
}

package com.example.jorge.testgithub;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


public class EditarParada extends Fragment implements OnMapReadyCallback, Paradas {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker marcador;
    private List<Parada> paradas;
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
    @BindView(R.id.carga)
    LinearLayout carga;
    @BindView(R.id.seleccionarParada)
    LinearLayout seleccionarParada;
    @BindView(R.id.layout)
    LinearLayout layout;


    public EditarParada() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //obtener paradas
        cargarParadas ();


        View v = inflater.inflate(R.layout.fragment_editar_parada, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        ButterKnife.bind(this, v);
        Button btn = (Button) v.findViewById(R.id.editarParada);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreError.setError(null);
                cantBicisError.setError(null);

                if (nombre.getText().toString().trim().equals("")) {
                    nombreError.setError("Nombre Invalido");
                    return;
                } else if (cantBicis.getText().toString().trim().equals("") || Integer.valueOf(cantBicis.getText().toString().trim()) < 1) {
                    cantBicisError.setError("Cantidad Invalida");
                    return;

                } else if (Integer.valueOf(cantBicis.getText().toString().trim()) > 20) {
                    cantBicisError.setError("Cantidad inválida (Máx 20)");
                    return;
                }
                int id = 0;

                for (Parada p : paradas)
                    if (p.getNombre().equals(marcador.getTitle()))
                        id = p.getId();


                modificarParada(id, nombre.getText().toString().trim(), getDireccion(marcador.getPosition()), marcador.getPosition().latitude, marcador.getPosition().longitude, Integer.valueOf(cantBicis.getText().toString().trim()));
            }
        });

        return v;
    }

    private void cargarParadas () {
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
                Log.d("ASD", "onFailure(PARADAS): ");
            }
        });
    }

    public void modificarParada(int id, String nombre, String direccion, double lat, double lng, int cantBicis) {
        Parada.editarParada(this, id, nombre, direccion, lat, lng, cantBicis);
    }

    private List<Marker> marks;

    public void prepararMapa() {
        Log.d("ASD", "prepararMapa: ");
        if (marks == null)
            marks = new ArrayList<>();

        for (Marker m : marks)
            m.remove();

        marks.clear();
        for (int i = 0; i < paradas.size(); i++) {
            Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(paradas.get(i).getLatitud(), paradas.get(i).getLongitud())).title(paradas.get(i).getNombre()).draggable(true).snippet(paradas.get(i).getDireccion()));
            m.setSnippet(paradas.get(i).getDireccion());
            marks.add (m);
        }

        carga.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcador = null;

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                               @Override
                                               public boolean onMarkerClick(Marker marker) {
                                                   marcador = marker;

                                                   nombre.setVisibility(View.VISIBLE);
                                                   cantBicis.setVisibility(View.VISIBLE);
                                                   editarParada.setVisibility(View.VISIBLE);
                                                   nombreError.setVisibility(View.VISIBLE);
                                                   cantBicisError.setVisibility(View.VISIBLE);
                                                   seleccionarParada.setVisibility(View.GONE);

                                                   cantBicisError.setError(null);
                                                   nombreError.setError(null);

                                                   for (Parada p : paradas) {
                                                       if (p.getNombre().equals(marcador.getTitle())) {
                                                           nombre.setText(p.getNombre());
                                                           cantBicis.setText(String.valueOf(p.getCantidadLibre() + p.getCantidadOcupada()));
                                                       }
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
                marcador = marker;
                nombre.setVisibility(View.VISIBLE);
                cantBicis.setVisibility(View.VISIBLE);
                editarParada.setVisibility(View.VISIBLE);
                nombreError.setVisibility(View.VISIBLE);
                cantBicisError.setVisibility(View.VISIBLE);
                cantBicisError.setError(null);
                nombreError.setError(null);

                for (Parada p : paradas) {
                    if (p.getNombre().equals(marcador.getTitle())) {
                        marcador.setSnippet(getDireccion(marcador.getPosition()));
                        nombre.setText(p.getNombre());
                        cantBicis.setText(String.valueOf(p.getCantidadLibre() + p.getCantidadOcupada()));
                    }
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
        return false;
    }

    @Override
    public boolean editarParada(boolean ok) {
        if (ok) {
            cargarParadas ();
            Toast.makeText(getActivity(), "PARADA EDITADA", Toast.LENGTH_LONG).show();
            nombre.setText("");
            cantBicis.setText("");
            nombre.setVisibility(View.INVISIBLE);
            cantBicis.setVisibility(View.INVISIBLE);
            editarParada.setVisibility(View.INVISIBLE);
            nombreError.setVisibility(View.INVISIBLE);
            cantBicisError.setVisibility(View.INVISIBLE);
            cantBicisError.setError(null);
            nombreError.setError(null);
        } else {
            Toast.makeText(getActivity(), "ERROR AL EDITAR", Toast.LENGTH_LONG).show();
        }

        return ok;
    }
}

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
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
//heatmaps.HeatmapTileProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaCalor extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Marker marcador;
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private List<LatLng> Heatlist;
    private List<Parada> paradas;
    @BindView(R.id.carga)
    LinearLayout carga;
    @BindView(R.id.layout)
    LinearLayout layout;


    public MapaCalor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //obtener paradas
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
        View v = inflater.inflate(R.layout.fragment_mapa_calor, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        ButterKnife.bind(this, v);
        return v;
    }

    public void prepararMapa() {

        /*for (int i = 0; i < paradas.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(paradas.get(i).getLatitud(), paradas.get(i).getLongitud())).title(paradas.get(i).getNombre()).draggable(true).snippet(paradas.get(i).getDireccion()));
        } */

        //obtener la posicion de todas las paradas y hacer los marcadores
        LatLng paysandu = new LatLng(-32.316465, -58.088980);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-32.317921, -58.089010)).title("Parada1").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paysandu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        carga.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcador = null;

        Heatlist = new ArrayList<>();

        if (paradas != null) {
            for (Parada p : paradas) {
                Heatlist.add(new LatLng(p.getLatitud(), p.getLongitud()));
            }
            //mProvider.setData(data); para cambiar el list
            //mOverlay.remove(); eliminar mapa de calor
            mProvider = new HeatmapTileProvider.Builder()
                    .data(Heatlist).build();
            mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        }

        if (paradas != null)
            prepararMapa();
    }
}

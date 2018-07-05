package com.example.jorge.testgithub;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaParadas;
import com.example.jorge.testgithub.Clases.Parada;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    FrameLayout layout;
    @BindView(R.id.fechaInicio)
    EditText fechaInicio;
    @BindView(R.id.fechaFin)
    EditText fechaFin;
    @BindView(R.id.btnMapaCalor)
    Button btnMapaCalor;


    public MapaCalor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mapa_calor, container, false);
        ButterKnife.bind(this, v);
        Calendar c = Calendar.getInstance();
        String hoy = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        bdMapaCalor(hoy, hoy);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        c.set(year, monthOfYear, dayOfMonth);
                        fechaInicio.setText(formato.format(c.getTime()));
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        c.set(year, monthOfYear, dayOfMonth);
                        fechaFin.setText(formato.format(c.getTime()));
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        btnMapaCalor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaInicio.getText().toString().trim().equals("") && fechaFin.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Seleccione una fecha", Toast.LENGTH_LONG).show();
                    return;
                }

                Date fechaI = null, fechaF = null;

                try {
                    fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    fechaF = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!fechaI.before(fechaF)) {
                    Toast.makeText(getContext(), "La fecha de inicio no es anterior a la de fin", Toast.LENGTH_LONG).show();
                    return;
                }

                if (fechaI != null && fechaF == null) {
                    String fecha = new SimpleDateFormat("yyyy-mm-dd").format(fechaI);
                    carga.setVisibility(View.VISIBLE);
                    bdMapaCalor(fecha, fecha);
                }

                if (fechaF != null && fechaI == null) {
                    String fecha = new SimpleDateFormat("yyyy-mm-dd").format(fechaF);
                    carga.setVisibility(View.VISIBLE);
                    bdMapaCalor(fecha, fecha);
                }

                if (fechaI != null && fechaF != null) {
                    String fecha = new SimpleDateFormat("yyyy-mm-dd").format(fechaI);
                    String fecha2 = new SimpleDateFormat("yyyy-mm-dd").format(fechaF);
                    carga.setVisibility(View.VISIBLE);
                    bdMapaCalor(fecha, fecha2);
                }
            }
        });


        return v;
    }

    public void bdMapaCalor(String fechaInicio, String fechaFin) {
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaParadas> call = bd.getParadasPorFecha(fechaInicio, fechaFin);
        call.enqueue(new Callback<RespuestaParadas>() {
            @Override
            public void onResponse(Call<RespuestaParadas> call, Response<RespuestaParadas> response) {
                paradas = new ArrayList<>();
                if (response.body().getCodigo().equals("1")) {
                   paradas = response.body().getParadas() != null ? response.body().getParadas() : new ArrayList<Parada>();
                    Log.d("ASD", "onResponse: "+ response.body().getParadas().size());
                }

                if (mMap != null) {
                    prepararMapa();
                    Log.d("ASD", "onResponse: "+ paradas.size());
                    cargarMapadeCalor(paradas);
                }
            }

            @Override
            public void onFailure(Call<RespuestaParadas> call, Throwable t) {

            }
        });
    }

    public void prepararMapa() {

        LatLng paysandu = new LatLng(-32.316465, -58.088980);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paysandu));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        carga.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    public void cargarMapadeCalor(List<Parada> paradas) {

        for (Parada p : paradas) {
            Heatlist.add(new LatLng(p.getLatitud(), p.getLongitud()));
        }
            if (Heatlist.size() != 0) {
                if (mProvider != null) {
                    mOverlay.remove();
                    mProvider.setData(Heatlist);
                } else {
                    mProvider = new HeatmapTileProvider.Builder()
                            .data(Heatlist).build();
                    mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                }

            }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marcador = null;

        Heatlist = new ArrayList<>();

        if (paradas != null) {
            prepararMapa();
            cargarMapadeCalor(paradas);
            //mProvider.setData(data); para cambiar el list
            //mOverlay.remove(); eliminar mapa de calor
        }
    }
}

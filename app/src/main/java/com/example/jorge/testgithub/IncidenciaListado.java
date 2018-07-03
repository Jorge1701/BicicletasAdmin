package com.example.jorge.testgithub;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jorge.testgithub.Adaptadores.AdaptadorListaIncidencias;
import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaIncidencias;
import com.example.jorge.testgithub.Clases.Incidencia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidenciaListado extends Fragment {

    @BindView(R.id.lvIncidencias)
    RecyclerView lvIncidencias;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;
    @BindView(R.id.noHay)
    LinearLayout noHay;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_listado_incidencias, container, false);
        ButterKnife.bind(this, v);

        recargarIncidencias(true);


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recargarIncidencias(false);
                swipeRefresh.setRefreshing(false);
            }
        });
        return v;
    }

    private void recargarIncidencias(boolean barraCarga) {
        if (barraCarga) {
            progressBar.setVisibility(View.VISIBLE);
        }

        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaIncidencias> call = bd.obtenerIncidencias();
        call.enqueue(new Callback<RespuestaIncidencias>() {
            @Override
            public void onResponse(Call<RespuestaIncidencias> call, Response<RespuestaIncidencias> response) {
                if (response.body().getIncidencias() != null) {
                    noHay.setVisibility(View.GONE);
                    cargarIncidencias(response.body().getIncidencias());
                } else {
                    Toast.makeText(IncidenciaListado.this.getActivity(), "No se pudieron cargar las incidencias", Toast.LENGTH_SHORT).show();
                    cargarIncidencias(new ArrayList<Incidencia>());
                    noHay.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RespuestaIncidencias> call, Throwable t) {
                noHay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Error de conexión con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void cargarIncidencias(List<Incidencia> incidencias) {
        lvIncidencias.setAdapter(new AdaptadorListaIncidencias(this.getContext(), incidencias));
        lvIncidencias.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvIncidencias.setHasFixedSize(true);

        if (incidencias.size() == 0)
            noHay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

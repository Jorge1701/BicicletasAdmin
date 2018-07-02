package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaParadas;
import com.example.jorge.testgithub.Clases.Parada;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParadasListado extends Fragment {
    private AgregarParadaInterface apListener;

    @BindView(R.id.lista_paradas)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.cargandoParadas)
    LinearLayout cargandoParadas;
    @BindView(R.id.noHayParadas)
    LinearLayout noHayParadas;
    ParadasListadoAdaptador adaptador;
    @BindView(R.id.floatingButtom)
    FloatingActionButton floatingActionButton;
    boolean alquileres;


    public ParadasListado() {
        alquileres = false;
    }

    public void setAlquileres(boolean alquileres) {
        this.alquileres = alquileres;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paradas_listado, container, false);
        ButterKnife.bind(this, view);

        bdCargarParadas();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bdCargarParadas();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apListener.abrirAgregarParada();
            }
        });

        return view;
    }

    private void bdCargarParadas() {
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaParadas> call = bd.getParadas();
        call.enqueue(new Callback<RespuestaParadas>() {
            @Override
            public void onResponse(Call<RespuestaParadas> call, Response<RespuestaParadas> response) {
                if (response.isSuccessful()) {
                    List<Parada> paradas = response.body().getParadas();
                    adaptador = new ParadasListadoAdaptador(getActivity(), paradas,apListener);
                    adaptador.setAlquileres(alquileres);
                    mRecyclerView.setAdapter(adaptador);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setHasFixedSize(true);
                    if (paradas.size() == 0) {
                        noHayParadas.setVisibility(View.VISIBLE);
                    }else {
                        noHayParadas.setVisibility(View.GONE);
                    }
                    cargandoParadas.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<RespuestaParadas> call, Throwable t) {
                noHayParadas.setVisibility(View.VISIBLE);
                cargandoParadas.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(getContext(), "Error de conexión con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AgregarParadaInterface) {
            apListener = (AgregarParadaInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AgregarParadaInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        apListener = null;
    }


    public interface AgregarParadaInterface {
        void abrirAgregarParada();
        void abrirEditarParada(String nomParada);
    }


}

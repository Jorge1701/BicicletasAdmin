package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaParadas;
import com.example.jorge.testgithub.Clases.Parada;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParadasListado extends Fragment {
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.lista_paradas)
    RecyclerView mRecyclerView;
    ParadasListadoAdaptador adaptador;
    boolean alquileres;


    public ParadasListado() {
        alquileres = false;
    }

    public boolean isAlquileres() {
        return alquileres;
    }

    public void setAlquileres(boolean alquileres) {
        this.alquileres = alquileres;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paradas_listado, container, false);
        ButterKnife.bind(this, view);

        if (!isAlquileres()) {
            BDInterface bd = BDCliente.getClient().create(BDInterface.class);
            Call<RespuestaParadas> call = bd.getParadas();
            call.enqueue(new Callback<RespuestaParadas>() {
                @Override
                public void onResponse(Call<RespuestaParadas> call, Response<RespuestaParadas> response) {
                    if (response.isSuccessful()) {
                        cargarParadas(response.body().getParadas(),false);
                    }
                }

                @Override
                public void onFailure(Call<RespuestaParadas> call, Throwable t) {

                }
            });
        } else {
            List<Parada> paradasAlquileres = new ArrayList<>();

            for(int i = 0 ; i <= 20; i++){
                Parada p = new Parada();
                p.setNombre("parada"+i);
                p.setCantAlquileresDia(i+1);
                p.setCantAlquileresSemana(i+5);
                p.setCantAlquileresMes(i+20);
                paradasAlquileres.add(p);
            }

            cargarParadas(paradasAlquileres, true);
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //------------------------------------------------------------------------------------------------------------------------------

    public void cargarParadas(List<com.example.jorge.testgithub.Clases.Parada> paradas, boolean alquileres) {
        adaptador = new ParadasListadoAdaptador(getActivity(), paradas);
        adaptador.setAlquileres(alquileres);
        mRecyclerView.setAdapter(adaptador);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
    }
}

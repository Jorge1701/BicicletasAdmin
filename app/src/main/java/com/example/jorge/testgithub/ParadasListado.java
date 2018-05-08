package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ParadasListado extends Fragment {
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.lista_paradas)
    RecyclerView mRecyclerView;
    ParadasListadoAdaptador adaptador;

    public ParadasListado() {
        // Required empty public constructor
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

        List<Parada> paradas = cargarParadas();
        adaptador = new ParadasListadoAdaptador(getActivity(), paradas);
        mRecyclerView.setAdapter(adaptador);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);


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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //------------------------------------------------------------------------------------------------------------------------------

    public List<Parada> cargarParadas() {
        List<Parada> paradas = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            paradas.add(new Parada(i, "Centro", "-232332223 324354524", "18 de Julio  y Montevideo", 20, 20));
            paradas.add(new Parada(i, "Playa", "-32424223 4465574524", "Av salto", 30, 10));
        }
        return paradas;
    }
}

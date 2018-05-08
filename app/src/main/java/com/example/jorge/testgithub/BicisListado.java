package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class BicisListado extends Fragment {
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.lista_bicis)
    RecyclerView mRecyclerView;
    BicisListadoAdaptador adaptador;

    public BicisListado() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bicis_listado, container, false);
        ButterKnife.bind(this, view);

        List<Bici> bicis = cargarBicis();
        adaptador = new BicisListadoAdaptador(getActivity(), bicis);
        mRecyclerView.setAdapter(adaptador);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public List<Bici> cargarBicis() {
        List<Bici> bicis = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            bicis.add(new Bici(i, "Disponible", new Parada(i, "Centro", "-232332223 324354524", "18 de Julio  y Montevideo", 20, 20)));
            bicis.add(new Bici(i, "Ocupada", new Parada(i + 5, "Centro", "-232332223 324354524", "18 de Julio  y Montevideo", 20, 20)));
        }

        return bicis;
    }

}

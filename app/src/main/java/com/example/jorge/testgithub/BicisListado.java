package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class BicisListado extends Fragment {
    private OnFragmentInteractionListener mListener;

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
        ListView listaBicis = view.findViewById(R.id.lista_bicis);

        List<Bici> bicis = cargarBicis();
        BicisListadoAdaptador bla = new BicisListadoAdaptador(getActivity(),R.layout.item_bicis_listado, bicis);
        listaBicis.setAdapter(bla);

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

    public List<Bici> cargarBicis(){
        List<Bici> bicis = new ArrayList<>();

        bicis.add(new Bici(1,"Disponible",new Parada(1,"Centro","-232332223 324354524","18 de Julio  y Montevideo",20,20)));
        bicis.add(new Bici(2,"Ocupada",new Parada(2,"Playa","-32424223 4465574524","Av salto",30,10)));
        bicis.add(new Bici(3,"Disponible",new Parada(3,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5)));
        bicis.add(new Bici(4,"Ocupada",new Parada(1,"Centro","-232332223 324354524","18 de Julio  y Montevideo",20,20)));

        return bicis;
    }

}

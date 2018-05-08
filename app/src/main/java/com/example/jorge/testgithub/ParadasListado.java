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


public class ParadasListado extends Fragment {
    private OnFragmentInteractionListener mListener;

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

        ListView listaParadas = view.findViewById(R.id.lista_paradas);

        List<Parada> paradas = cargarParadas();
        ParadasListadoAdaptador pla = new ParadasListadoAdaptador(getActivity(),R.layout.item_paradas_listado, paradas);
        listaParadas.setAdapter(pla);
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

    public List<Parada> cargarParadas(){
        List<Parada> paradas = new ArrayList<>();

        paradas.add(new Parada(1,"Centro","-232332223 324354524","18 de Julio  y Montevideo",20,20));
        paradas.add(new Parada(2,"Playa","-32424223 4465574524","Av salto",30,10));
        paradas.add(new Parada(3,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(4,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(5,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(6,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(7,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(8,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(9,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(10,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(11,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(12,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(13,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(14,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(15,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));
        paradas.add(new Parada(16,"Trebol","-234324354524 463154163","Ruta 90 km 2",10,5));

        return paradas;
    }
}

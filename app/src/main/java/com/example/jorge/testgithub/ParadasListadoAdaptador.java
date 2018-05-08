package com.example.jorge.testgithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ParadasListadoAdaptador extends ArrayAdapter<Parada> {
        private Context context;
        private List<Parada> paradas = new ArrayList<>();
        private int resource;

        public ParadasListadoAdaptador(@NonNull Context context,int resource, List<Parada> paradas) {
                super(context, resource , paradas);
                this.context = context;
                this.paradas = paradas;
                this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View listItem = convertView;
                if(listItem == null)
                        listItem = LayoutInflater.from(context).inflate(R.layout.item_paradas_listado,parent,false);

            Parada paradaActual = paradas.get(position);

                TextView nombreParada = (TextView) listItem.findViewById(R.id.nombreParada);
                nombreParada.setText("#" + paradaActual.getNumero() + " " + paradaActual.getNombre());
                TextView estadoParada = (TextView) listItem.findViewById(R.id.estadoParada);
                estadoParada.setText(paradaActual.getEstado());
                if(paradaActual.getEstado().equals("Libre")){
                    estadoParada.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
                }else{
                    estadoParada.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
                }

                TextView cantBicisLibres = (TextView) listItem.findViewById(R.id.cantBicisLibres);
                cantBicisLibres.setText("" + paradaActual.getCantBicisLibres());

                TextView cantBicisOcupadas = (TextView) listItem.findViewById(R.id.cantBicisOcupadas);
                cantBicisOcupadas.setText("" + paradaActual.getCantBicisOcupadas());

                return listItem;
        }
}


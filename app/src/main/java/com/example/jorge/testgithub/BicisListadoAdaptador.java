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

public class BicisListadoAdaptador extends ArrayAdapter<Bici>{
    private Context context;
    private List<Bici> bicis = new ArrayList<>();
    private int resource;

    public BicisListadoAdaptador(@NonNull Context context,int resource, List<Bici> bicis) {
        super(context, resource , bicis);
        this.context = context;
        this.bicis = bicis;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.item_bicis_listado,parent,false);

        Bici biciActual = bicis.get(position);

        TextView idBici = (TextView) listItem.findViewById(R.id.idBici);
        idBici.setText("" + biciActual.getId());

        TextView estadoBici = (TextView) listItem.findViewById(R.id.estadoBici);
        estadoBici.setText(biciActual.getEstado());

        if(biciActual.getEstado().equals("Disponible")){
            estadoBici.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
        }else{
            estadoBici.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
        }

        TextView paradaBici = (TextView) listItem.findViewById(R.id.paradaBici);
        paradaBici.setText("" + biciActual.getParada().getNumero());

        return listItem;
    }
}

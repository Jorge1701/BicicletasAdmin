package com.example.jorge.testgithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

class AdapatadorListaParadas extends ArrayAdapter<String> {

    public AdapatadorListaParadas(@NonNull Context context, String[] parametros) {
        super(context, R.layout.item_lista_paradas,parametros);
    }
}

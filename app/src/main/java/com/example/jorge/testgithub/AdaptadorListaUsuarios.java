package com.example.jorge.testgithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdaptadorListaUsuarios extends ArrayAdapter<Usuario> {

    public AdaptadorListaUsuarios (Context context, List<Usuario> usuarios) {
        super (context, R.layout.item_lista, usuarios);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from (getContext ());
        View v = i.inflate (R.layout.item_lista, parent, false);

        String nombre = getItem (position).getNombre ();
        boolean activo = getItem (position).isValidado();

		Button btnHabilitar = (Button) v.findViewById (R.id.btnHabilitar);
		Button btnInhabilitar = (Button) v.findViewById (R.id.btnInhabilitar);

		btnHabilitar.setVisibility (!activo ? View.VISIBLE : View.GONE);
		btnInhabilitar.setVisibility (activo ? View.VISIBLE : View.GONE);

		btnHabilitar.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				getItem (position).habilitar ();
			}
		});

		btnInhabilitar.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				getItem (position).inhabilitar ();
			}
		});

        ((TextView) v.findViewById (R.id.tvNombre)).setText (nombre);

        return v;
    }
}

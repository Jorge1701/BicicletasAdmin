package com.example.jorge.testgithub;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaUsuarios extends ArrayAdapter<Usuario> {

    public AdaptadorListaUsuarios (Context context, List<Usuario> usuarios) {
        super (context, R.layout.item_lista_usuario, usuarios);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from (getContext ());
        View v = i.inflate (R.layout.item_lista_usuario, parent, false);

        // TODO: Cargar imagen del usuario
        String nombre = getItem (position).getNombre ();
        boolean activo = getItem (position).isValidado();

		Button btnHabilitar = (Button) v.findViewById (R.id.btnHabilitar);
		Button btnInhabilitar = (Button) v.findViewById (R.id.btnInhabilitar);

		btnHabilitar.setVisibility (!activo ? View.VISIBLE : View.GONE);
		btnInhabilitar.setVisibility (activo ? View.VISIBLE : View.GONE);

		btnHabilitar.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Habilitar (getItem (position));
			}
		});

		btnInhabilitar.setOnClickListener (new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				Inhabilitar (getItem (position));
			}
		});

        ((TextView) v.findViewById (R.id.tvNombre)).setText (nombre);

        return v;
    }

    private void Habilitar (final Usuario u) {
		final Dialog dialog = new Dialog(getContext (), android.R.style.Theme_Translucent_NoTitleBar);
		View view = LayoutInflater.from(getContext ()).inflate(R.layout.custom_alert, null);

		((TextView) view.findViewById (R.id.tvTitulo)).setText ("Habilitar " + u.getNombre () + "?");

		view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				u.habilitar ();
				dialog.dismiss();
			}
		});

		dialog.setContentView(view);
		dialog.show();
	}

	private void Inhabilitar (final Usuario u) {
		final Dialog dialog = new Dialog(getContext (), android.R.style.Theme_Translucent_NoTitleBar);
		View view = LayoutInflater.from(getContext ()).inflate(R.layout.custom_alert, null);

		((TextView) view.findViewById (R.id.tvTitulo)).setText ("Inhabilitar " + u.getNombre () + "?");

		view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				u.inhabilitar ();
				dialog.dismiss();
			}
		});

		dialog.setContentView(view);
		dialog.show();
	}
}

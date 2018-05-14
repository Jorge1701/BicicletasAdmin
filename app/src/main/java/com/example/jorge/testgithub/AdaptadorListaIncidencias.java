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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaIncidencias extends ArrayAdapter<Incidencia> {

	public AdaptadorListaIncidencias(Context context, List<Incidencia> incidencias) {
		super (context, R.layout.item_incidencia, incidencias);
	}

	@NonNull
	@Override
	public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		LayoutInflater i = LayoutInflater.from (getContext ());
		final View v = i.inflate (R.layout.item_incidencia, parent, false);

		// TODO: Cargar imagen del usuario
		((TextView) v.findViewById (R.id.tvNombreUsuario)).setText (getItem (position).getUsuario ().getNombre ());
		Button estado = (Button) v.findViewById (R.id.btnEstado);
		final TextView tvAsignado = ((TextView) v.findViewById (R.id.tvNombreAsignado));

		switch (getItem (position).getEstado ()) {
			case ABIERTA:
				tvAsignado.setText ("");
				estado.setTextColor (0xFFFF4444);
				estado.setText ("ABIERTA");
				break;
			case ASIGNADA:
				tvAsignado.setText (getItem (position).getAsignado ());
				estado.setTextColor (0xFFFF9D00);
				estado.setText ("ASIGNADA");
				break;
			case RESUELTA:
				tvAsignado.setText (getItem (position).getAsignado ());
				estado.setTextColor (0xFF35D002);
				estado.setText ("RESUELTA");
				break;
		}

		estado.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (getItem (position).getEstado ()) {
					case ABIERTA:
						Asignar (getItem (position));
						break;
					case ASIGNADA:
						Resolver (getItem (position));
						break;
				}

				notifyDataSetChanged  ();
			}
		});

		((TextView) v.findViewById (R.id.tvParadaIncidencia)).setText (getItem (position).getParada () == -1 ? "---" : getItem (position).getParada () + "");
		((TextView) v.findViewById (R.id.tvDescripcion)).setText (getItem (position).getIncidencia ());

		final TextView tvTituloComentarios = v.findViewById (R.id.tvTituloComentarios);
		final LinearLayout ll = v.findViewById (R.id.llComentarios);
		cargarComentarios (v.getContext (), tvTituloComentarios, ll, getItem (position).getComentarios ());
		final EditText etComentar = (EditText) v.findViewById (R.id.etComentar);

		v.findViewById (R.id.ivSendComentar).setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick(View v2) {
				if (etComentar.getText ().toString ().equals (""))
					return;

				// TODO: Mandar nombre del admin logueado (En vez de "Jorge")
				getItem (position).comentar (new ComentarioDeAdmin ("Jorge", etComentar.getText ().toString ()));
				cargarComentarios (v.getContext (), tvTituloComentarios, ll, getItem (position).getComentarios ());
				etComentar.setText ("");
			}
		});

		return v;
	}

	private void cargarComentarios (Context contexto, TextView tv, LinearLayout ll, List<ComentarioDeAdmin> comentarios) {
		if (comentarios == null || comentarios.size () == 0) {
			tv.setVisibility (View.GONE);
			ll.setVisibility (View.GONE);
		} else {
			if (ll.getChildCount () > 0)
				ll.removeAllViews ();

			tv.setVisibility (View.VISIBLE);
			ll.setVisibility (View.VISIBLE);

			for (ComentarioDeAdmin c: comentarios) {
				View v = LayoutInflater.from (contexto).inflate (R.layout.item_comentario, null, false);
				((TextView) v.findViewById (R.id.tvNombreAdmin)).setText (c.getAdmin ());
				((TextView) v.findViewById (R.id.tvComentario)).setText (c.getComentario ());
				ll.addView (v);
			}
		}
	}

	private void Asignar (final Incidencia i) {
		final Dialog dialog = new Dialog(getContext (), android.R.style.Theme_Translucent_NoTitleBar);
		View view = LayoutInflater.from(getContext ()).inflate(R.layout.custom_dialog_alert, null);

		((TextView) view.findViewById (R.id.tvTitulo)).setText ("Asignar Incidencia?");
		final EditText etNombreAsigando = ((EditText) view.findViewById (R.id.etNombreAsignado));

		view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				i.setEstado (Incidencia.Estado.ASIGNADA);
				i.setAsignado (etNombreAsigando.getText ().toString ());
				notifyDataSetChanged ();
				dialog.dismiss();
			}
		});

		dialog.setContentView(view);
		dialog.show();
	}

	private void Resolver (final Incidencia i) {
		final Dialog dialog = new Dialog(getContext (), android.R.style.Theme_Translucent_NoTitleBar);
		View view = LayoutInflater.from(getContext ()).inflate(R.layout.custom_alert, null);

		((TextView) view.findViewById (R.id.tvTitulo)).setText ("Resolver Incidencia?");

		view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				i.setEstado (Incidencia.Estado.RESUELTA);
				notifyDataSetChanged ();
				dialog.dismiss();
			}
		});

		dialog.setContentView(view);
		dialog.show();
	}
}

package com.example.jorge.testgithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

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
		TextView estado = (TextView) v.findViewById (R.id.tvEstadoIncidencia);

		switch (getItem (position).getEstado ()) {
			case ABIERTA:
				estado.setTextColor (0xFFFF4444);
				estado.setText ("SIN RESOLVER");
				break;
			case ASIGNADA:
				estado.setTextColor (0xFFFF9D00);
				estado.setText ("ASIGNADA");
				break;
			case RESUELTA:
				estado.setTextColor (0xFF35D002);
				estado.setText ("RESUELTA");
				break;
		}

		((TextView) v.findViewById (R.id.tvParadaIncidencia)).setText (getItem (position).getParada () == -1 ? "---" : getItem (position).getParada () + "");
		((TextView) v.findViewById (R.id.tvDescripcion)).setText (getItem (position).getIncidencia ());

		final TextView tvTituloComentarios = v.findViewById (R.id.tvTituloComentarios);
		final LinearLayout ll = v.findViewById (R.id.llComentarios);
		cargarComentarios (v.getContext (), tvTituloComentarios, ll, getItem (position).getComentarios ());
		final EditText etComentar = (EditText) v.findViewById (R.id.etComentar);

		v.findViewById (R.id.ivSendComentar).setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick(View v2) {
				getItem (position).comentar (etComentar.getText ().toString ());
				cargarComentarios (v.getContext (), tvTituloComentarios, ll, getItem (position).getComentarios ());
				etComentar.setText ("");
			}
		});

		return v;
	}

	private void cargarComentarios (Context contexto, TextView tv, LinearLayout ll, List<String> comentarios) {
		if (comentarios == null || comentarios.size () == 0) {
			tv.setVisibility (View.GONE);
			ll.setVisibility (View.GONE);
		} else {
			if (ll.getChildCount () > 0)
				ll.removeAllViews ();

			tv.setVisibility (View.VISIBLE);
			ll.setVisibility (View.VISIBLE);

			for (String c: comentarios) {
				View v = LayoutInflater.from (contexto).inflate (R.layout.item_comentario, null, false);
				((TextView) v.findViewById (R.id.tvComentario)).setText (c);
				ll.addView (v);
			}
		}
	}
}

package com.example.jorge.testgithub;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapdadorListaIncidencias extends ArrayAdapter<Incidencia> {

	public AdapdadorListaIncidencias (Context context, List<Incidencia> incidencias) {
		super (context, R.layout.item_incidencia, incidencias);
	}

	@NonNull
	@Override
	public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		LayoutInflater i = LayoutInflater.from (getContext ());
		View v = i.inflate (R.layout.item_incidencia, parent, false);

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

		return v;
	}
}

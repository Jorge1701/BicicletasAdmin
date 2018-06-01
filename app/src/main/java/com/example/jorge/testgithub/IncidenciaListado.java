package com.example.jorge.testgithub;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorge.testgithub.Clases.Incidencia;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncidenciaListado extends Fragment {

	@BindView(R.id.lvIncidencias)
	RecyclerView lvIncidencias;

	private UsuariosListado.OnFragmentInteractionListener mListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_listado_incidencias, container, false);
		ButterKnife.bind (this, v);

		lvIncidencias.setAdapter (new AdaptadorListaIncidencias (getActivity (), Incidencia.cargarIncidencias ()));
		lvIncidencias.setLayoutManager (new LinearLayoutManager (getActivity ()));
		lvIncidencias.setHasFixedSize (true);

		return v;
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof UsuariosListado.OnFragmentInteractionListener) {
			mListener = (UsuariosListado.OnFragmentInteractionListener) context;
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
		void onFragmentInteraction(Uri uri);
	}
}

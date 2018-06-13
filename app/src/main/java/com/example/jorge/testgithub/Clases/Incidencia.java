package com.example.jorge.testgithub.Clases;

import android.util.Log;
import android.widget.Toast;

import com.example.jorge.testgithub.AdaptadorListaIncidencias;
import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Incidencia {

	private int id;
	private String usuario;
	private String parada;
	private int estado;
	private String admin;
	private String comentario;
	private String fecha;
	private List<ComentarioIncidencia> comentariosIncidencia;

	public Incidencia(int id, String usuario, String parada, int estado, String admin, String comentario, String fecha, List<ComentarioIncidencia> comentarioIncidencias) {
		this.id = id;
		this.usuario = usuario;
		this.parada = parada;
		this.estado = estado;
		this.admin = admin;
		this.comentario = comentario;
		this.fecha = fecha;
		this.comentariosIncidencia = comentarioIncidencias;
	}

	public void comentar (final AdaptadorListaIncidencias.IncidenciaViewHolder i, final String admin, final String comentario) {
		if (comentariosIncidencia == null)
			comentariosIncidencia = new ArrayList<>();

		BDInterface bd = BDCliente.getClient().create(BDInterface.class);
		Log.d ("ASD", admin + ", " + id + ", " + comentario);
		Call<Respuesta> call = bd.agregarComentario (admin, id, comentario);
		call.enqueue(new Callback<Respuesta>() {
			@Override
			public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
				Log.d ("ASD", "onResponse");
				if (response.body().getCodigo().equals("-1"))
					i.errorAlComentar();
				else {
					comentariosIncidencia.add (new ComentarioIncidencia (admin, comentario));
					i.cargarComentarios();
				}
			}

			@Override
			public void onFailure(Call<Respuesta> call, Throwable t) {
				Log.d ("ASD", "onFailure");
				Log.d ("ASD", t.getMessage ());
				i.errorAlComentar ();
			}
		});
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getParada() {
		return parada;
	}

	public void setParada(String parada) {
		this.parada = parada;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<ComentarioIncidencia> getComentarioIncidencias() {
		return comentariosIncidencia;
	}

	public void setComentarioIncidencias(List<ComentarioIncidencia> comentarioIncidencias) {
		this.comentariosIncidencia = comentarioIncidencias;
	}
}
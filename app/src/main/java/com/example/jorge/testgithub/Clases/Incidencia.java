package com.example.jorge.testgithub.Clases;

import java.util.ArrayList;

public class Incidencia {

	private int id;
	private String usuario;
	private String parada;
	private int estado;
	private String admin;
	private String comentario;
	private String fecha;
	private ArrayList<ComentarioIncidencia> comentarioIncidencias;

	public Incidencia(int id, String usuario, String parada, int estado, String admin, String comentario, String fecha, ArrayList<ComentarioIncidencia> comentarioIncidencias) {
		this.id = id;
		this.usuario = usuario;
		this.parada = parada;
		this.estado = estado;
		this.admin = admin;
		this.comentario = comentario;
		this.fecha = fecha;
		this.comentarioIncidencias = comentarioIncidencias;
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

	public ArrayList<ComentarioIncidencia> getComentarioIncidencias() {
		return comentarioIncidencias;
	}

	public void setComentarioIncidencias(ArrayList<ComentarioIncidencia> comentarioIncidencias) {
		this.comentarioIncidencias = comentarioIncidencias;
	}
}
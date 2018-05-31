package com.example.jorge.testgithub.Clases;

import com.example.jorge.testgithub.ComentarioDeAdmin;

import java.util.ArrayList;
import java.util.List;

public class Incidencia {

	public enum Estado {
		ABIERTA, ASIGNADA, RESUELTA
	}

	private Usuario usuario;
	private int parada;
	private Estado estado;
	private String asignado;
	private String incidencia;
	private List<ComentarioDeAdmin> comentarios;

	public Incidencia(Usuario usuario, int parada, Estado estado, String incidencia, List<ComentarioDeAdmin> comentarios) {
		this.usuario = usuario;
		this.parada = parada;
		this.estado = estado;
		this.incidencia = incidencia;
		this.comentarios = comentarios;
	}

	private static List<Incidencia> incidencias;

	public static List<Incidencia> cargarIncidencias () {
		if (incidencias != null)
			return incidencias;

		// TODO: Cargar Incidencias desde la base de datos
		incidencias = new ArrayList<>();

		return incidencias;
	}

	public void comentar (ComentarioDeAdmin comentario) {
		// TODO: Agregar el comentario a la base de datos
		if (comentarios == null)
			comentarios = new ArrayList<>();

		comentarios.add (comentario);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getParada() {
		return parada;
	}

	public void setParada(int parada) {
		this.parada = parada;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		// TODO: Cambiar estado en BD
		this.estado = estado;
	}

	public String getAsignado() {
		return asignado;
	}

	public void setAsignado(String asignado) {
		// TODO: Cambiar asignado en BD
		this.asignado = asignado;
	}

	public static List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public static void setIncidencias(List<Incidencia> incidencias) {
		Incidencia.incidencias = incidencias;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public List<ComentarioDeAdmin> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<ComentarioDeAdmin> comentarios) {
		this.comentarios = comentarios;
	}
}
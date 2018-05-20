package com.example.jorge.testgithub;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Incidencia {

	enum Estado {
		ABIERTA, ASIGNADA, RESUELTA
	}

	private Usuario usuario;
	private int parada;
	private Estado estado;
	private String incidencia;
	private List<String> comentarios;

	public Incidencia(Usuario usuario, int parada, Estado estado, String incidencia, List<String> comentarios) {
		this.usuario = usuario;
		this.parada = parada;
		this.estado = estado;
		this.incidencia = incidencia;
		this.comentarios = comentarios;
	}

	public static List<Incidencia> cargarIncidencias () {
		// TODO: Cargar Incidencias desde la base de datos
		List<Incidencia> incidencias = new ArrayList<>();

		incidencias.add (new Incidencia (new Usuario ("Jorge Rosas", true), -1, Incidencia.Estado.ABIERTA, "Se pincho la rueda", null));
		incidencias.add (new Incidencia (new Usuario ("Luis Etchebarne", true), 12, Incidencia.Estado.ABIERTA, "Asdj dfg jlak jfdfg fldf hlhas ldhdgklg sdflkjd f kahf f la  f kbklf askfjb  fbsgksd akf fbala kjdgbdlkg ", null));

		List<String> c1 = new ArrayList<>();
		c1.add ("1");
		c1.add ("1");
		c1.add ("1");

		incidencias.add (new Incidencia (new Usuario ("Alejandro Peculio", true), 3, Estado.ASIGNADA, "asf ljd kgsdkjg akls dkbsg lsfd kgkakf bksdbjg abff klg lkdbg k", c1));

		List<String> c2 = new ArrayList<>();
		c2.add ("2");

		incidencias.add (new Incidencia (new Usuario ("Brian Gomez", true), -1, Estado.RESUELTA, "as  dgjk gsdkl alskh ldskg dg kl", c2));

		return incidencias;
	}

	public void comentar (String comentario) {
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
		this.estado = estado;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public List<String> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<String> comentarios) {
		this.comentarios = comentarios;
	}
}
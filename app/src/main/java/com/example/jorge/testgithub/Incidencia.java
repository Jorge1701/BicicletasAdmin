package com.example.jorge.testgithub;

import java.util.ArrayList;
import java.util.List;

public class Incidencia {

	enum Estado {
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

		incidencias.add (new Incidencia (new Usuario ("Jorge Rosas", true), -1, Incidencia.Estado.ABIERTA, "Se pincho la rueda", null));
		incidencias.add (new Incidencia (new Usuario ("Luis Etchebarne", true), 12, Incidencia.Estado.ABIERTA, "Asdj dfg jlak jfdfg fldf hlhas ldhdgklg sdflkjd f kahf f la  f kbklf askfjb  fbsgksd akf fbala kjdgbdlkg ", null));

		List<ComentarioDeAdmin> c1 = new ArrayList<>();
		c1.add (new ComentarioDeAdmin ("Jorge", "Hola"));
		c1.add (new ComentarioDeAdmin ("Ale", "Hola"));
		c1.add (new ComentarioDeAdmin ("Brian", "Hola"));

		Incidencia i1 = new Incidencia (new Usuario ("Alejandro Peculio", true), 3, Estado.ASIGNADA, "asf ljd kgsdkjg akls dkbsg lsfd kgkakf bksdbjg abff klg lkdbg k", c1);
		i1.setAsignado ("Brian");
		incidencias.add (i1);

		List<ComentarioDeAdmin> c2 = new ArrayList<>();
		c2.add (new ComentarioDeAdmin ("Jorge", "Hola"));

		Incidencia i2 = new Incidencia (new Usuario ("Brian Gomez", true), -1, Estado.RESUELTA, "as  dgjk gsdkl alskh ldskg dg kl", c2);
		i2.setAsignado ("Luis");
		incidencias.add (i2);

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
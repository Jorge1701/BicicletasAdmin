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
	private String incidencia;

	public Incidencia(Usuario usuario, int parada, Estado estado, String incidencia) {
		this.usuario = usuario;
		this.parada = parada;
		this.estado = estado;
		this.incidencia = incidencia;
	}

	public static List<Incidencia> cargarIncidencias () {
		// TODO: Cargar Incidencias desde la base de datos
		List<Incidencia> incidencias = new ArrayList<>();

		incidencias.add (new Incidencia (new Usuario ("Jorge Rosas", true), -1, Incidencia.Estado.ABIERTA, "Se pincho la rueda"));
		incidencias.add (new Incidencia (new Usuario ("Luis Etchebarne", true), 12, Incidencia.Estado.ABIERTA, "Asdj dfg jlak jfdfg fldf hlhas ldhdgklg sdflkjd f kahf f la  f kbklf askfjb  fbsgksd akf fbala kjdgbdlkg "));
		incidencias.add (new Incidencia (new Usuario ("Alejandro Peculio", true), 3, Estado.ASIGNADA, "asf ljd kgsdkjg akls dkbsg lsfd kgkakf bksdbjg abff klg lkdbg k"));
		incidencias.add (new Incidencia (new Usuario ("Brian Gomez", true), -1, Estado.RESUELTA, "as  dgjk gsdkl alskh ldskg dg kl"));

		incidencias.add (new Incidencia (new Usuario ("Luis Etchebarne", true), 12, Incidencia.Estado.ABIERTA, "Asdj dfg jlak jfdfg fldf hlhas ldhdgklg sdflkjd f kahf f la  f kbklf askfjb  fbsgksd akf fbala kjdgbdlkg "));
		incidencias.add (new Incidencia (new Usuario ("Alejandro Peculio", true), 3, Estado.ASIGNADA, "asf ljd kgsdkjg akls dkbsg lsfd kgkakf bksdbjg abff klg lkdbg k"));
		incidencias.add (new Incidencia (new Usuario ("Brian Gomez", true), -1, Estado.RESUELTA, "as  dgjk gsdkl alskh ldskg dg kl"));

		incidencias.add (new Incidencia (new Usuario ("Luis Etchebarne", true), 12, Incidencia.Estado.ABIERTA, "Asdj dfg jlak jfdfg fldf hlhas ldhdgklg sdflkjd f kahf f la  f kbklf askfjb  fbsgksd akf fbala kjdgbdlkg "));
		incidencias.add (new Incidencia (new Usuario ("Alejandro Peculio", true), 3, Estado.ASIGNADA, "asf ljd kgsdkjg akls dkbsg lsfd kgkakf bksdbjg abff klg lkdbg k"));
		incidencias.add (new Incidencia (new Usuario ("Brian Gomez", true), -1, Estado.RESUELTA, "as  dgjk gsdkl alskh ldskg dg kl"));

		return incidencias;
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
}
package com.example.jorge.testgithub.BD;

import com.example.jorge.testgithub.Clases.Incidencia;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaIncidencias {

	@SerializedName("codigo")
	private String codigo;
	@SerializedName("mensaje")
	private String mensaje;
	@SerializedName("incidencias")
	private List<Incidencia> incidencias;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}
}

package com.example.jorge.testgithub.BD;

import com.example.jorge.testgithub.Clases.Administrador;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaAdministradores {

	@SerializedName("codigo")
	private String codigo;
	@SerializedName ("mensaje")
	private String mensaje;
	@SerializedName ("administradores")
	private List<Administrador> administradores;

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

	public List<Administrador> getAdministradores() {
		return administradores;
	}

	public void setAdministradores(List<Administrador> administradores) {
		this.administradores = administradores;
	}
}

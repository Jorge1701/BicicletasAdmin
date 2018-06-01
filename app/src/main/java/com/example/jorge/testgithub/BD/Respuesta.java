package com.example.jorge.testgithub.BD;

import com.google.gson.annotations.SerializedName;

public class Respuesta {

	@SerializedName ("codigo")
	private String codigo;
	@SerializedName ("mensaje")
	private String mensaje;

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
}

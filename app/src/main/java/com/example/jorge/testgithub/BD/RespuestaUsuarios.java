package com.example.jorge.testgithub.BD;

import com.example.jorge.testgithub.Clases.Usuario;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaUsuarios {

	@SerializedName ("codigo")
	private String codigo;
	@SerializedName ("mensaje")
	private String mensaje;
	@SerializedName("usuarios")
	private List<Usuario> usuarios;

	public String getCodigo() { return codigo; }

	public void setCodigo(String codigo) { this.codigo = codigo; }

	public String getMensaje() { return mensaje; }

	public void setMensaje(String mensaje) { this.mensaje = mensaje; }

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}

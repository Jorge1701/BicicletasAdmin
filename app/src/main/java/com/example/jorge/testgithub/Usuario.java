package com.example.jorge.testgithub;

public class Usuario {

	private String nombre;
	private boolean validado;

	public Usuario (String nombre, boolean validado) {
		this.nombre = nombre;

		this.validado = validado;
	}

	public void habilitar () {
		// TODO: Habilitar el usuario
	}

	public void inhabilitar () {
		// TODO: Inhabilitar el usuario
	}

	public String getNombre () {
		return nombre;
	}

	public void setNombre (String nombre) {
		this.nombre = nombre;
	}

	public boolean isValidado () {
		return validado;
	}

	public void setValidado (boolean validado) {
		this.validado = validado;
	}

	public static boolean verificarUsuario (String usuario, String password) {
		// TODO: Verificar usuario con la base de datos
		return true;
	}
}

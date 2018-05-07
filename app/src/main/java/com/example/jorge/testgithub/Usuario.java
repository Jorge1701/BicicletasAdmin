package com.example.jorge.testgithub;

import java.util.ArrayList;
import java.util.List;

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

	public static List<Usuario> cargarUsuarios () {
		List<Usuario> usuarios = new ArrayList<>();

		// TODO: Cargar usuarios de la bd

		usuarios.add (new Usuario ("Jorgea", true));
		usuarios.add (new Usuario ("Brian", false));
		usuarios.add (new Usuario ("Ale", true));
		usuarios.add (new Usuario ("Luisa", false));
		usuarios.add (new Usuario ("Jorgea", true));
		usuarios.add (new Usuario ("Brian", false));
		usuarios.add (new Usuario ("Ale", true));
		usuarios.add (new Usuario ("Luisa", false));
		usuarios.add (new Usuario ("Jorgea", true));
		usuarios.add (new Usuario ("Brian", false));
		usuarios.add (new Usuario ("Ale", true));
		usuarios.add (new Usuario ("Luisa", false));
		usuarios.add (new Usuario ("Jorgea", true));
		usuarios.add (new Usuario ("Brian", false));
		usuarios.add (new Usuario ("Ale", true));
		usuarios.add (new Usuario ("Luisa", false));

		return usuarios;
	}
}

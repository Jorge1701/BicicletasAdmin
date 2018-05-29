package com.example.jorge.testgithub;

import android.util.Log;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.Util.Login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

	public static void verificarUsuario (final Login l, final String usuario, final String password) {
		BDInterface bd = BDCliente.getClient().create(BDInterface.class);
		Call<Respuesta> call = bd.login ("Anda a cagar", password, usuario);
		call.enqueue(new Callback<Respuesta>() {
			@Override
			public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
				l.verificarLogin(response.body().getCodigo().equals("1"), usuario, password);
			}

			@Override
			public void onFailure(Call<Respuesta> call, Throwable t) {

			}
		});
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

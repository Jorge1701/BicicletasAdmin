package com.example.jorge.testgithub.Clases;

import android.util.Log;
import android.view.View;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.Util.Login;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.jorge.testgithub.AdaptadorListaUsuarios.UsuarioViewHolder;

public class Usuario {

	private String Email;
	private String Cedula;
	private String Pasaporte;
	private String Pass;
	private String Nombre;
	private String Telefono;
	private String Direccion;
	private int Activado;
	private int Saldo;
	private int IdCelular;
	private int cantidadAlquileres;

	public Usuario(String email, String cedula, String pasaporte, String pass, String nombre, String telefono, String direccion, int activado, int saldo, int idCelular, int cantidadAlquileres) {
		Email = email;
		Cedula = cedula;
		Pasaporte = pasaporte;
		Pass = pass;
		Nombre = nombre;
		Telefono = telefono;
		Direccion = direccion;
		Activado = activado;
		Saldo = saldo;
		IdCelular = idCelular;
		this.cantidadAlquileres = cantidadAlquileres;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCedula() {
		return Cedula;
	}

	public void setCedula(String cedula) {
		Cedula = cedula;
	}

	public String getPasaporte() {
		return Pasaporte;
	}

	public void setPasaporte(String pasaporte) {
		Pasaporte = pasaporte;
	}

	public String getPass() {
		return Pass;
	}

	public void setPass(String pass) {
		Pass = pass;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public int isActivado() {
		return Activado;
	}

	public void setActivado(int activado) {
		Activado = activado;
	}

	public int getSaldo() {
		return Saldo;
	}

	public void setSaldo(int saldo) {
		Saldo = saldo;
	}

	public int getIdCelular() {
		return IdCelular;
	}

	public void setIdCelular(int idCelular) {
		IdCelular = idCelular;
	}

	public int getCantidadAlquileres() {
		return cantidadAlquileres;
	}

	public void setCantidadAlquileres(int cantidadAlquileres) {
		this.cantidadAlquileres = cantidadAlquileres;
	}

	public void habilitar (final UsuarioViewHolder v) {
		BDInterface bd = BDCliente.getClient().create(BDInterface.class);
		Call<Respuesta> call = bd.habilitar(Email);
		call.enqueue(new Callback<Respuesta>() {
			@Override
			public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
				if (response.body().getCodigo().equals("1")) {
					v.barraProgreso.setVisibility (View.GONE);
					v.btnHabilitar.setVisibility(View.GONE);
					v.btnInhabilitar.setVisibility(View.VISIBLE);
					Activado = 1;
				}
			}

			@Override
			public void onFailure(Call<Respuesta> call, Throwable t) {
				v.barraProgreso.setVisibility (View.GONE);
				v.btnHabilitar.setVisibility(View.VISIBLE);
				v.btnInhabilitar.setVisibility(View.GONE);
				Log.d ("FALLO", t.getMessage ());
			}
		});
	}

	public void inhabilitar (final UsuarioViewHolder v) {
		BDInterface bd = BDCliente.getClient().create(BDInterface.class);
		Call<Respuesta> call = bd.inhabilitar(Email);
		call.enqueue(new Callback<Respuesta>() {
			@Override
			public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
				if (response.body().getCodigo().equals("1")) {
					v.barraProgreso.setVisibility (View.GONE);
					v.btnHabilitar.setVisibility (View.VISIBLE);
					v.btnInhabilitar.setVisibility (View.GONE);
					Activado = 0;
				}
			}

			@Override
			public void onFailure(Call<Respuesta> call, Throwable t) {
				v.barraProgreso.setVisibility (View.GONE);
				v.btnHabilitar.setVisibility (View.GONE);
				v.btnInhabilitar.setVisibility (View.VISIBLE);
				Log.d ("FALLO", t.getMessage ());
			}
		});
	}

	public static void verificarUsuario (final Login l, final String usuario, final String password) {
		BDInterface bd = BDCliente.getClient().create(BDInterface.class);
		Call<Respuesta> call = bd.login ("Andate a cagar", password, usuario);
		call.enqueue(new Callback<Respuesta>() {
			@Override
			public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
				Log.d ("ASD", response.body().getCodigo());
				l.verificarLogin(response.body().getCodigo().equals("1"), usuario, password);
			}

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("Error","No se pudo conectar al servidor");
            }
        });
    }
}

package com.example.jorge.testgithub.Clases;

public class Administrador {

	private String Email;
	private String Pass;

	public Administrador() {
	}

	public Administrador(String email, String pass) {
		Email = email;
		Pass = pass;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPass() {
		return Pass;
	}

	public void setPass(String pass) {
		Pass = pass;
	}
}

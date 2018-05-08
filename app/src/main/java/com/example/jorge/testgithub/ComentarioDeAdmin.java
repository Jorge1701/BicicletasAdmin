package com.example.jorge.testgithub;

public class ComentarioDeAdmin {

	private String admin;
	private String comentario;

	public ComentarioDeAdmin(String admin, String comentario) {
		this.admin = admin;
		this.comentario = comentario;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}

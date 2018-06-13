package com.example.jorge.testgithub.Clases;

public class ComentarioIncidencia {

	private int id;
	private String admin;
	private int incidencia;
	private String comentario;
	private String fecha;

	public ComentarioIncidencia(int id, String admin, int incidencia, String comentario, String fecha) {
		this.id = id;
		this.admin = admin;
		this.incidencia = incidencia;
		this.comentario = comentario;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public int getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(int incidencia) {
		this.incidencia = incidencia;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}

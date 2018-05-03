package com.example.jorge.testgithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdmin extends AppCompatActivity {

	@BindView (R.id.toolbar)
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_admin);
		ButterKnife.bind (this);

		setSupportActionBar (toolbar);
		getSupportActionBar ().setTitle ("Menu Administrador");
		toolbar.setTitleTextColor (Color.parseColor ("#FFFFFF"));

		// Se cargan los datos del usuario guardado
		SharedPreferences sp = getSharedPreferences ("usuario_guardado", MODE_PRIVATE);
		String usuario = sp.getString ("usuario", null);
		String password = sp.getString ("password", null);

		SharedPreferences sp_tmp = getSharedPreferences ("usuario_guardado_temp", MODE_PRIVATE);
		String usuario_tmp = sp_tmp.getString ("usuario", null);
		String password_tmp = sp_tmp.getString ("password", null);

		String u = "";
		String p = "";

		if (usuario_tmp != null && password_tmp != null) {
			Toast.makeText(this, "TEMP", Toast.LENGTH_SHORT).show();
			u = usuario_tmp;
			p = usuario_tmp;
		} else if (usuario != null && password != null) {
			Toast.makeText(this, "PERMA", Toast.LENGTH_SHORT).show();
			u = usuario;
			p = password;
		}

		// Si hay datos se verifica que sean validos
		if (u.isEmpty () && p.isEmpty () || !Usuario.verificarUsuario (u, p))
			// En caso de que el usuario fuera eliminado mientras tenia la aplicacion cerrada se borran los datos y se muestra un mensaje
			cerrarSesion ();
	}

	private void cerrarSesion () {
		SharedPreferences sp = getSharedPreferences ("usuario_guardado", MODE_PRIVATE);
		SharedPreferences.Editor ed = sp.edit();
		ed.clear();
		ed.commit();

		Intent i = new Intent(MenuAdmin.this, AdminLogin.class);
		i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity (i);
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		MenuInflater inflater = getMenuInflater ();
		inflater.inflate (R.menu.menu_item, menu);
		return super.onCreateOptionsMenu (menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.cerrarSesion:
				cerrarSesion();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onStop() {
		SharedPreferences sp = getSharedPreferences ("usuario_guardado_temp", MODE_PRIVATE);
		SharedPreferences.Editor ed = sp.edit();
		ed.clear();
		ed.commit();
		super.onStop();
	}

	public void ListaoUsuarios (View v) {
		startActivity (new Intent (MenuAdmin.this, ListadoUsuarios.class));
	}
}

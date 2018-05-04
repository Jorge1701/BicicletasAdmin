package com.example.jorge.testgithub;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListadoIncidencias extends AppCompatActivity {

	@BindView (R.id.lvIncidencias)
	ListView lvIncidencias;
	@BindView (R.id.toolbar)
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listado_incidencias);
		ButterKnife.bind (this);

		setSupportActionBar (toolbar);
		getSupportActionBar ().setTitle ("Listado de Incidencias");
		toolbar.setTitleTextColor (Color.parseColor ("#FFFFFF"));

		lvIncidencias.setAdapter (new AdapdadorListaIncidencias (this, Incidencia.cargarIncidencias ()));
	}
}

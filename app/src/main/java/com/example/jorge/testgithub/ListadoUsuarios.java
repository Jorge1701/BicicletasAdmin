package com.example.jorge.testgithub;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListadoUsuarios extends AppCompatActivity {

	@BindView (R.id.toolbar)
    Toolbar toolbar;
	@BindView (R.id.search)
    MaterialSearchView searchView;
	@BindView (R.id.lvLista)
    ListView lvLista;
	@BindView (R.id.cbSinValidar)
    CheckBox cbSinValidar;

    private String filtro;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_usuarios);
		ButterKnife.bind (this);

        setSupportActionBar (toolbar);
        getSupportActionBar ().setTitle ("Lista de Usuarios");
        toolbar.setTitleTextColor (Color.parseColor ("#FFFFFF"));

        cargarUsuarios ();
        filtrarUsuarios ();

        searchView.setOnSearchViewListener (new MaterialSearchView.SearchViewListener () {
            @Override
            public void onSearchViewShown () {
            }

            @Override
            public void onSearchViewClosed () {
                filtro = null;
                filtrarUsuarios ();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit (String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange (String newText) {
                filtro = newText;
                filtrarUsuarios ();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.search_item, menu);
        MenuItem item = menu.findItem (R.id.action_search);
        searchView.setMenuItem (item);
        return true;
    }

    public void FiltrarSinValidar (View v) {
        filtrarUsuarios ();
    }

    private void filtrarUsuarios () {
        List<Usuario> usuarios = cargarUsuarios ();

        if (filtro != null && !filtro.isEmpty ())
            for (int i = usuarios.size() - 1; i >= 0; i--)
                if (!usuarios.get (i).getNombre ().toLowerCase ().contains (filtro.toLowerCase ()))
                    usuarios.remove (i);

        if (cbSinValidar.isChecked ())
            for (int i = usuarios.size() - 1; i >= 0; i--)
                if (usuarios.get (i).isValidado ())
                    usuarios.remove (i);

        lvLista.setAdapter (new AdaptadorListaUsuarios (this, usuarios));
    }

    private List<Usuario> cargarUsuarios () {
        List<Usuario> usuarios = new ArrayList<> ();

        // TODO: Cargar usuarios de la bd

        usuarios.add (new Usuario ("Jorgea", true));
        usuarios.add (new Usuario ("Brian", false));
        usuarios.add (new Usuario ("Ale", true));
        usuarios.add (new Usuario ("Luisa", false));

        return usuarios;
    }
}

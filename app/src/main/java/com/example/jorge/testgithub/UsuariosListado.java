package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UsuariosListado extends Fragment {

    @BindView (R.id.search)
    MaterialSearchView searchView;
    @BindView (R.id.lvLista)
    ListView lvLista;
    @BindView (R.id.cbSinValidar)
    CheckBox cbSinValidar;

    private String filtro;

    private OnFragmentInteractionListener mListener;

    public UsuariosListado() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usuarios_listado, container, false);
        ButterKnife.bind(this,v);

        cbSinValidar.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                filtrarUsuarios ();
            }
        });

        filtrarUsuarios ();

        searchView.setOnSearchViewListener (new MaterialSearchView.SearchViewListener () {
            @Override
            public void onSearchViewShown () {
            	searchView.setVisibility (View.VISIBLE);
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

        return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search_item, menu);
		MenuItem item = menu.findItem (R.id.action_search);
		searchView.setMenuItem (item);

	}

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void filtrarUsuarios () {
        List<Usuario> usuarios = Usuario.cargarUsuarios ();

        if (filtro != null && !filtro.isEmpty ())
            for (int i = usuarios.size() - 1; i >= 0; i--)
                if (!usuarios.get (i).getNombre ().toLowerCase ().contains (filtro.toLowerCase ()))
                    usuarios.remove (i);

        if (cbSinValidar.isChecked ())
            for (int i = usuarios.size() - 1; i >= 0; i--)
                if (usuarios.get (i).isValidado ())
                    usuarios.remove (i);

        lvLista.setAdapter (new AdaptadorListaUsuarios(this.requireContext(), usuarios));
    }
}

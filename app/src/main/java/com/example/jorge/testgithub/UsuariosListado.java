package com.example.jorge.testgithub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UsuariosListado extends Fragment{

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usuarios_listado, container, false);
        ButterKnife.bind(this,v);


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

        return v;


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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

        lvLista.setAdapter (new AdaptadorListaUsuarios(this.requireContext(), usuarios));
    }

    private List<Usuario> cargarUsuarios () {
        List<Usuario> usuarios = new ArrayList<>();

        // TODO: Cargar usuarios de la bd

        usuarios.add (new Usuario ("Jorgea", true));
        usuarios.add (new Usuario ("Brian", false));
        usuarios.add (new Usuario ("Ale", true));
        usuarios.add (new Usuario ("Luisa", false));

        return usuarios;
    }
}

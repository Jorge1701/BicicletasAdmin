package com.example.jorge.testgithub;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import retrofit2.Call;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jorge.testgithub.Adaptadores.AdaptadorListaUsuarios;
import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaUsuarios;
import com.example.jorge.testgithub.Clases.Usuario;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;


public class UsuariosListado extends Fragment {
    @BindView(R.id.search)
    MaterialSearchView searchView;
    @BindView(R.id.lvLista)
    RecyclerView lvLista;
    @BindView(R.id.cbSinValidar)
    CheckBox cbSinValidar;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;
    @BindView(R.id.llNoHay)
    LinearLayout llNoHay;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;

    private List<Usuario> usuarios;

    private String filtro;

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
        ButterKnife.bind(this, v);

        cbSinValidar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bdcargarUsuarios(true);
            }
        });

        bdcargarUsuarios(true);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                filtro = null;
                bdcargarUsuarios(true);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtro = newText;
                bdcargarUsuarios(true);
                return true;
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bdcargarUsuarios(false);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        return v;
    }

    public void bdcargarUsuarios(boolean barraCarga) {
        llNoHay.setVisibility(View.GONE);
        if (barraCarga) {
            progressBar.setVisibility(View.VISIBLE);
        }


        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaUsuarios> call = bd.obtenerUsuarios();
        call.enqueue(new Callback<RespuestaUsuarios>() {
            @Override
            public void onResponse(Call<RespuestaUsuarios> call, Response<RespuestaUsuarios> response) {
                filtrarUsuarios(response.body().getUsuarios());
            }

            @Override
            public void onFailure(Call<RespuestaUsuarios> call, Throwable t) {
                llNoHay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(UsuariosListado.this.getContext(), "Error de conexion con el servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private List<Usuario> copiarUsuarios() {
        List<Usuario> res = new ArrayList<>();
        if (usuarios != null)
            for (Usuario u : usuarios)
                res.add(u);
        return res;
    }

    private void filtrarUsuarios(List<Usuario> usuarios) {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }

        if (filtro != null && !filtro.isEmpty())
            for (int i = usuarios.size() - 1; i >= 0; i--)
                if (!usuarios.get(i).getNombre().toLowerCase().contains(filtro.toLowerCase()))
                    usuarios.remove(i);

        if (cbSinValidar.isChecked())
            for (int i = usuarios.size() - 1; i >= 0; i--)
                if (usuarios.get(i).isActivado() == 1)
                    usuarios.remove(i);

        lvLista.setAdapter(new AdaptadorListaUsuarios(getActivity(), usuarios));
        lvLista.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvLista.setHasFixedSize(true);

        swipeRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);

        if (usuarios.size() == 0) {
            llNoHay.setVisibility(View.VISIBLE);
        } else {
            llNoHay.setVisibility(View.GONE);
        }
    }
}

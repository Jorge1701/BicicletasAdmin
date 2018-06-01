    package com.example.jorge.testgithub;

    import android.content.Context;
    import android.net.Uri;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;

    import com.example.jorge.testgithub.BD.BDCliente;
    import com.example.jorge.testgithub.BD.BDInterface;
    import com.example.jorge.testgithub.BD.RespuestaBicicletas;
    import com.example.jorge.testgithub.Clases.Bicicleta;
    import com.miguelcatalan.materialsearchview.MaterialSearchView;
    import java.util.ArrayList;
    import java.util.List;
    import butterknife.BindView;
    import butterknife.ButterKnife;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;


    public class BicisListado extends Fragment {
        private OnFragmentInteractionListener mListener;
        private List<Bicicleta> bicicletas = null;

        @BindView (R.id.search)
        MaterialSearchView searchView;
        @BindView(R.id.lista_bicis)
        RecyclerView mRecyclerView;
        BicisListadoAdaptador adaptador;

        public BicisListado() {
            // Required empty public constructor
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        private int filtro = -1;

        private void filtrarBicis () {

            if(this.bicicletas != null){

               List<Bicicleta> bs = new ArrayList<>(this.bicicletas);

                if (filtro != -1) {
                    for (int i = bs.size() - 1; i >= 0; i--) {
                        if (!bs.get(i).getParada().equals(String.valueOf(filtro)))
                            bs.remove(i);
                    }
                    cargarBicicletas(bs);
                    if(bs.size() == 0){
                        Toast.makeText(getContext(),"No se encontraron resultados",Toast.LENGTH_SHORT).show();
                    }
                }else{
                     cargarBicicletas(this.bicicletas);
                }

            }

        }



        private void cargarBicicletas(List<Bicicleta> bicicletas){
            adaptador = new BicisListadoAdaptador(getActivity(), bicicletas);
            mRecyclerView.setAdapter(adaptador);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setHasFixedSize(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view = inflater.inflate(R.layout.fragment_bicis_listado, container, false);
            ButterKnife.bind(this, view);

            BDInterface bd = BDCliente.getClient().create(BDInterface.class);
            Call<RespuestaBicicletas> call = bd.getBicicletas();
            call.enqueue(new Callback<RespuestaBicicletas>() {
                @Override
                public void onResponse(Call<RespuestaBicicletas> call, Response<RespuestaBicicletas> response) {
                    if (response.isSuccessful()) {
                        bicicletas = response.body().getBicicletas();
                        cargarBicicletas(response.body().getBicicletas());
                    }
                }
                @Override
                public void onFailure(Call<RespuestaBicicletas> call, Throwable t) {

                }
            });

            searchView.setOnSearchViewListener (new MaterialSearchView.SearchViewListener () {
                @Override
                public void onSearchViewShown () {
                    searchView.setVisibility (View.VISIBLE);
                }

                @Override
                public void onSearchViewClosed () {
                    filtro = -1;
                    filtrarBicis();
                }
            });

            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener () {
                @Override
                public boolean onQueryTextSubmit (String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange (String newText) {
                    filtro = (newText.equals ("") || !esNumero (newText)) ? -1 : Integer.valueOf (newText);
                    filtrarBicis ();
                    return true;
                }
            });

            return view;

        }

        private boolean esNumero (String texto) {
            try {
                Integer.valueOf (texto);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.search_item, menu);
            MenuItem item = menu.findItem (R.id.action_search);
            searchView.setMenuItem (item);
        }

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

    }

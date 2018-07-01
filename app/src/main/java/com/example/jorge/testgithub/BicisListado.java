package com.example.jorge.testgithub;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaBicicletas;
import com.example.jorge.testgithub.Clases.Bicicleta;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BicisListado extends Fragment {
    private OnFragmentInteractionListener mListener;
    private List<Bicicleta> bicicletas = null;
    private List<Bicicleta> bNoDevueltas = null;

    @BindView(R.id.search)
    MaterialSearchView searchView;
    @BindView(R.id.lista_bicis)
    RecyclerView mRecyclerView;
    @BindView(R.id.checkboxNoDevueltas)
    CheckBox noDevueltas;
    @BindView(R.id.spinnerFechas)
    Spinner fechas;
    @BindView(R.id.noHayBicicletas)
    LinearLayout noHayBicicletas;
    @BindView(R.id.cargandoBicicletas)
    LinearLayout cargandoBicicletas;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    BicisListadoAdaptador adaptador;
    String itemSeleccionado = "";

    public BicisListado() {
        // Required empty public constructor
    }

    public CheckBox getNoDevueltas() {
        return noDevueltas;
    }

    public void setNoDevueltas(CheckBox noDevueltas) {
        this.noDevueltas = noDevueltas;
    }

    public List<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(List<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private int filtro = -1;

    private void filtrarBicis(List<Bicicleta> bicicletas) {

        if (bicicletas != null) {

            List<Bicicleta> bs = new ArrayList<>(bicicletas);

            if (filtro != -1) {
                for (int i = bs.size() - 1; i >= 0; i--) {
                    if (noDevueltas.isChecked()) {
                        if (!bs.get(i).getId().contains(String.valueOf(filtro)))
                            bs.remove(i);
                    } else {
                        if (!bs.get(i).getParada().contains(String.valueOf(filtro)))
                            bs.remove(i);
                    }

                }
                cargarBicicletas(bs);
                if (bs.size() == 0) {
                    Toast.makeText(getContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
            } else {
                cargarBicicletas(bicicletas);
            }

        }

    }

    private void cargarBicicletas(List<Bicicleta> bicicletas) {
        adaptador = new BicisListadoAdaptador(getActivity(), bicicletas);
        mRecyclerView.setAdapter(adaptador);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        if (bicicletas.size() == 0) {
            noHayBicicletas.setVisibility(View.VISIBLE);
        } else {
            noHayBicicletas.setVisibility(View.GONE);
        }
        cargandoBicicletas.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    private void fitrarBicicletasDia(String fecha) {

        if(bicicletas == null) {
            return;
        }

        List<Bicicleta> bs = new ArrayList<>(bicicletas);


        for (int i = bs.size() - 1; i >= 0; i--) {
            if (bs.get(i).getParada() == "" && bs.get(i).getFechaAlquiler() != null) {
                if (!bs.get(i).getFechaAlquiler().split(" ")[0].equals(fecha))
                    bs.remove(i);
            } else {
                bs.remove(i);
            }

        }
        bNoDevueltas = bs;
        cargarBicicletas(bs);


    }

    private void cargarBicicletasNoDevueltas(final String fechaSeleccionada) {
        if (noDevueltas.isChecked()) {

            final Calendar hoy = Calendar.getInstance();
            final SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            final String fechaHoy = formato.format(hoy.getTime());
            hoy.add(Calendar.DAY_OF_MONTH, -1);
            final String fechaAyer = formato.format(hoy.getTime());

            final List<String> contfechas = new ArrayList<>();
            contfechas.add("Hoy");
            contfechas.add("Ayer");
            if (!fechaSeleccionada.equals("")) {
                if (!fechaSeleccionada.equals(fechaHoy) && !fechaSeleccionada.equals(fechaAyer)) {
                    contfechas.add(fechaSeleccionada);
                }
            }
            contfechas.add("Seleccionar fecha");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, contfechas);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fechas.setAdapter(adapter);
            if (!fechaSeleccionada.equals("")) {
                if (fechaSeleccionada.equals(fechaHoy)) {
                    fechas.setSelection(adapter.getPosition("Hoy"));
                } else if (fechaSeleccionada.equals(fechaAyer)) {
                    fechas.setSelection(adapter.getPosition("Ayer"));
                } else {
                    fechas.setSelection(adapter.getPosition(fechaSeleccionada));
                }
            }
            fechas.setVisibility(View.VISIBLE);
            searchView.dismissSuggestions();


            fechas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    itemSeleccionado = parent.getItemAtPosition(position).toString();

                    if (itemSeleccionado.equals("Hoy")) {

                        fitrarBicicletasDia(fechaHoy);

                    } else if (itemSeleccionado.equals("Ayer")) {

                        fitrarBicicletasDia(fechaAyer);

                    } else if (itemSeleccionado.equals("Seleccionar fecha")) {

                        final Calendar calendario = Calendar.getInstance();
                        int yy = calendario.get(Calendar.YEAR);
                        int mm = calendario.get(Calendar.MONTH);
                        int dd = calendario.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);
                                cargarBicicletasNoDevueltas(formato.format(c.getTime()));
                            }
                        }, yy, mm, dd);
                        datePicker.show();

                    } else {
                        fitrarBicicletasDia(itemSeleccionado);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else

        {
            fechas.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>()));
            fechas.setVisibility(View.GONE);
            cargarBicicletas(bicicletas);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bicis_listado, container, false);
        ButterKnife.bind(this, view);

        bdCargarBicicletas();

        noDevueltas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cargarBicicletasNoDevueltas("");
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                filtro = -1;
                filtrarBicis((noDevueltas.isChecked()) ? bNoDevueltas : bicicletas);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtro = (newText.equals("") || !esNumero(newText)) ? -1 : Integer.valueOf(newText);
                filtrarBicis((noDevueltas.isChecked()) ? bNoDevueltas : bicicletas);
                return true;
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bdCargarBicicletas();
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;

    }

    private void bdCargarBicicletas() {
        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<RespuestaBicicletas> call = bd.getBicicletas();
        call.enqueue(new Callback<RespuestaBicicletas>() {
            @Override
            public void onResponse(Call<RespuestaBicicletas> call, Response<RespuestaBicicletas> response) {
                if (response.isSuccessful()) {
                    bicicletas = response.body().getBicicletas();
                    if (noDevueltas.isChecked()) {
                        fitrarBicicletasDia(itemSeleccionado);
                    } else {
                        cargarBicicletas(bicicletas);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaBicicletas> call, Throwable t) {
                noHayBicicletas.setVisibility(View.VISIBLE);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private boolean esNumero(String texto) {
        try {
            Integer.valueOf(texto);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
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

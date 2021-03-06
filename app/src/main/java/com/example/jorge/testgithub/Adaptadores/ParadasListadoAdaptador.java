package com.example.jorge.testgithub.Adaptadores;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.jorge.testgithub.Clases.Parada;
import com.example.jorge.testgithub.ParadasListado;
import com.example.jorge.testgithub.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ParadasListadoAdaptador extends RecyclerView.Adapter<ParadasListadoAdaptador.ParadaViewHolder> {
    private Context mContext;
    private List<Parada> mParadas;
    private boolean alquileres;
    private ParadasListado.AgregarParadaInterface mListener;

    public ParadasListadoAdaptador(Context context, List<Parada> paradas, ParadasListado.AgregarParadaInterface agregarParadaInterface) {
        mContext = context;
        mParadas = paradas;
        mListener = agregarParadaInterface;
    }

    public boolean isAlquileres() {
        return alquileres;
    }

    public void setAlquileres(boolean alquileres) {
        this.alquileres = alquileres;
    }

    @Override
    public ParadasListadoAdaptador.ParadaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (alquileres) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paradas_listado_alquileres, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paradas_listado, parent, false);
        }

        ParadaViewHolder viewHolder = new ParadaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ParadasListadoAdaptador.ParadaViewHolder holder, int position) {
        YoYo.with(Techniques.ZoomIn).duration(500).playOn(holder.itemView);
        holder.bindParada(mParadas.get(position));
    }

    @Override
    public int getItemCount() {
        return mParadas.size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewAttachedToWindow(ParadaViewHolder paradaViewHolder) {
        super.onViewAttachedToWindow(paradaViewHolder);
        //Agregar animacion circular
        //animateCircularReveal(paradaViewHolder.itemView);
    }

    //Animacion circular
    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateCircularReveal(View view){
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(),view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view,centerX,centerY,startRadius,endRadius);

        view.setVisibility(View.VISIBLE);
        animation.start();

    }*/

    public class ParadaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.nombreParada)
        TextView nombreParada;
        private Context mContext;


        public ParadaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindParada(Parada parada) {
            if (isAlquileres()) {
                nombreParada.setText(parada.getNombre2());
                //Graphica
                final GraphView graph = (GraphView) itemView.findViewById(R.id.graph);
                if (graph.getSeries().size() == 0) {
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                            new DataPoint(0, (parada.getAlquileresPorDia() != null && parada.getAlquileresPorDia() != "") ? Double.valueOf(parada.getAlquileresPorDia()) : 0),
                            new DataPoint(1, (parada.getAlquileresPorSemana() != null && parada.getAlquileresPorSemana() != "") ? Double.valueOf(parada.getAlquileresPorSemana()) : 0),
                            new DataPoint(2, (parada.getAlquileresPorMes() != null && parada.getAlquileresPorMes() != "") ? Double.valueOf(parada.getAlquileresPorMes()) : 0),
                    });
                    graph.addSeries(series);

                    // Estilo
                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                        }
                    });

                    series.setSpacing(50);

                    // Colocar valores encima de la barra
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.RED);


                    //Cambiar las etiquetas
                    StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                    staticLabelsFormatter.setDynamicLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                // show normal x values
                                return super.formatLabel(value, isValueX);
                            } else {
                                // show currency for y values
                                return super.formatLabel(value, isValueX);
                            }
                        }
                    });
                    staticLabelsFormatter.setHorizontalLabels(new String[]{"Dia", "Semana", "Mes"});
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.setOnClickListener(this);
                }
            } else {
                nombreParada.setText(parada.getNombre());
                TextView numeroParada = itemView.findViewById(R.id.numeroParada);
                TextView cantBicisLibres = itemView.findViewById(R.id.cantBicisLibres);
                TextView cantBicisOcupadas = itemView.findViewById(R.id.cantBicisOcupadas);

                numeroParada.setText("" + parada.getId());
                cantBicisLibres.setText("" + parada.getCantidadLibre());
                cantBicisOcupadas.setText("" + parada.getCantidadOcupada());
            }
        }

        @Override
        public void onClick(View v) {
            mListener.abrirEditarParada(this.nombreParada.getText().toString());
        }
    }
}


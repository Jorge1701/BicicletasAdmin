package com.example.jorge.testgithub;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParadasListadoAdaptador extends RecyclerView.Adapter<ParadasListadoAdaptador.ParadaViewHolder> {
    private Context mContext;
    private List<Parada> mParadas;

    public ParadasListadoAdaptador(Context context, List<Parada> paradas) {
        mContext = context;
        mParadas = paradas;
    }

    @Override
    public ParadasListadoAdaptador.ParadaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paradas_listado, parent, false);
        ParadaViewHolder viewHolder = new ParadaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ParadasListadoAdaptador.ParadaViewHolder holder, int position) {
        YoYo.with(Techniques.ZoomIn).duration(500).playOn(holder.cardView);
        holder.bindParada(mParadas.get(position));
    }

    @Override
    public int getItemCount() {
        return mParadas.size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewAttachedToWindow(ParadaViewHolder paradaViewHolder){
        super.onViewAttachedToWindow(paradaViewHolder);
        //animateCircularReveal(paradaViewHolder.itemView);
    }

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

    public class ParadaViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.nombreParada)
        TextView nombreParada;
        @BindView(R.id.numeroParada)
        TextView numeroParada;
        @BindView(R.id.cantBicisLibres)
        TextView cantBicisLibres;
        @BindView(R.id.cantBicisOcupadas)
        TextView cantBicisOcupadas;
        CardView cardView;

        private Context mContext;

        public ParadaViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            cardView  = itemView.findViewById(R.id.item_paradas_listado);
        }

        public void bindParada(Parada parada){
            nombreParada.setText(parada.getNombre());
            numeroParada.setText(""+parada.getNumero());
            cantBicisLibres.setText(""+parada.getCantBicisLibres());
            cantBicisOcupadas.setText(""+parada.getCantBicisOcupadas());
        }
    }
}


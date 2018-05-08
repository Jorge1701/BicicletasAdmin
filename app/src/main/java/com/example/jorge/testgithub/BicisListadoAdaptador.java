package com.example.jorge.testgithub;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BicisListadoAdaptador extends RecyclerView.Adapter<BicisListadoAdaptador.BicisViewHolder>{
    private Context mContext;
    private List<Bici> mBicis = new ArrayList<>();

    public BicisListadoAdaptador(Context context, List<Bici> bicis) {
        mContext = context;
        mBicis = bicis;
    }

    @Override
    public BicisListadoAdaptador.BicisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bicis_listado, parent, false);
        BicisListadoAdaptador.BicisViewHolder viewHolder = new BicisListadoAdaptador.BicisViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BicisListadoAdaptador.BicisViewHolder holder, int position) {
        holder.bindBici(mBicis.get(position));
    }

    @Override
    public int getItemCount() {
        return mBicis.size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewAttachedToWindow(BicisListadoAdaptador.BicisViewHolder bicisViewHolder){
        super.onViewAttachedToWindow(bicisViewHolder);
        animateCircularReveal(bicisViewHolder.itemView);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateCircularReveal(View view){
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(),view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view,centerX,centerY,startRadius,endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    public class BicisViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.idBici)
        TextView idBici;
        @BindView(R.id.estadoBici)
        TextView estadoBici;
        @BindView(R.id.paradaBici)
        TextView paradaBici;

        private Context mContext;

        public BicisViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

        public void bindBici(Bici bici){
            idBici.setText(""+bici.getId());
            estadoBici.setText(bici.getEstado());
            if(bici.getEstado().equals("Ocupada")){
                estadoBici.setTextColor(mContext.getResources().getColor(R.color.red));
            }else{
                estadoBici.setTextColor(mContext.getResources().getColor(R.color.green));
            }
            paradaBici.setText(""+bici.getParada().getNumero());
        }
    }

}

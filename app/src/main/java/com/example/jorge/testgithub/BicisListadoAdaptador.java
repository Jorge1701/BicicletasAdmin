package com.example.jorge.testgithub;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jorge.testgithub.Clases.Bicicleta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BicisListadoAdaptador extends RecyclerView.Adapter<BicisListadoAdaptador.BicisViewHolder>{
    private Context mContext;
    private List<Bicicleta> mBicicletas = new ArrayList<>();

    public BicisListadoAdaptador(Context context, List<Bicicleta> bicicletas) {
        mContext = context;
        mBicicletas = bicicletas;
    }

    @Override
    public BicisListadoAdaptador.BicisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bicis_listado, parent, false);
        BicisListadoAdaptador.BicisViewHolder viewHolder = new BicisListadoAdaptador.BicisViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BicisListadoAdaptador.BicisViewHolder holder, int position) {
        YoYo.with(Techniques.ZoomIn).duration(500).playOn(holder.cardView);
        holder.bindBici(mBicicletas.get(position));
    }

    @Override
    public int getItemCount() {
        return mBicicletas.size();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewAttachedToWindow(BicisListadoAdaptador.BicisViewHolder bicisViewHolder){
        super.onViewAttachedToWindow(bicisViewHolder);
       // animateCircularReveal(bicisViewHolder.itemView);

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

    public class BicisViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.idBici)
        TextView idBici;
        @BindView(R.id.estadoBici)
        TextView estadoBici;
        @BindView(R.id.paradaBici)
        TextView paradaBici;
        CardView cardView;

        private Context mContext;

        public BicisViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
            cardView = itemView.findViewById(R.id.item_bicis_listado);
        }

        public void bindBici(Bicicleta bicicleta){
            idBici.setText(""+ bicicleta.getId());
            estadoBici.setText((bicicleta.getDisponible().equals("false")) ? "Disponible": "Ocupada");
            if(bicicleta.getDisponible().equals("false")){
                estadoBici.setTextColor(mContext.getResources().getColor(R.color.green));
            }else{
                estadoBici.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            paradaBici.setText(""+ (bicicleta.getParada().equals("") ? "-" :bicicleta.getParada()));
        }
    }

}

package com.example.jorge.testgithub;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorListaUsuarios extends RecyclerView.Adapter<AdaptadorListaUsuarios.UsuarioViewHolder> {

	private Context mContext;
	private List<Usuario> mUsuarios;

    public AdaptadorListaUsuarios (Context context, List<Usuario> usuarios) {
        mContext = context;
        mUsuarios = usuarios;
    }

	@Override
	public AdaptadorListaUsuarios.UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_usuario, parent, false);
		AdaptadorListaUsuarios.UsuarioViewHolder viewHolder = new AdaptadorListaUsuarios.UsuarioViewHolder (view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(AdaptadorListaUsuarios.UsuarioViewHolder holder, int position) {
		YoYo.with(Techniques.ZoomIn).duration(500).playOn(holder.cardView);
		holder.bindUsuario(mUsuarios.get(position));
	}

	@Override
	public int getItemCount() {
		return mUsuarios.size();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onViewAttachedToWindow(AdaptadorListaUsuarios.UsuarioViewHolder usuarioViewHolder){
		super.onViewAttachedToWindow(usuarioViewHolder);
		//animateCircularReveal(paradaViewHolder.itemView);
	}

	public class UsuarioViewHolder extends RecyclerView.ViewHolder {
    	@BindView (R.id.tvTitulo)
		TextView tvTitulo;
		@BindView (R.id.btnHabilitar)
		Button btnHabilitar;
		@BindView (R.id.btnInhabilitar)
		Button btnInhabilitar;
		@BindView (R.id.item_lista_usuario)
		CardView cardView;

		private Context mContext;

		public UsuarioViewHolder (View itemView) {
			super (itemView);
			ButterKnife.bind (this, itemView);
			mContext = itemView.getContext ();
		}

		public void bindUsuario (final Usuario u) {
			tvTitulo.setText (u.getNombre ());
			// TODO: Cargar imagen del usuario
			boolean activo = u.isValidado ();

			btnHabilitar.setVisibility (!activo ? View.VISIBLE : View.GONE);
			btnInhabilitar.setVisibility (activo ? View.VISIBLE : View.GONE);

			btnHabilitar.setOnClickListener (new View.OnClickListener () {
				@Override
				public void onClick (View v) {
					Habilitar (u);
				}
			});

			btnInhabilitar.setOnClickListener (new View.OnClickListener() {
				@Override
				public void onClick (View v) {
					Inhabilitar (u);
				}
			});
		}

		private void Habilitar (final Usuario u) {
			final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
			View view = LayoutInflater.from(mContext).inflate(R.layout.custom_alert, null);

			tvTitulo.setText ("Habilitar " + u.getNombre () + "?");

			view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					u.habilitar ();
					dialog.dismiss();
				}
			});

			dialog.setContentView(view);
			dialog.show();
		}

		private void Inhabilitar (final Usuario u) {
			final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
			View view = LayoutInflater.from(mContext).inflate(R.layout.custom_alert, null);

			tvTitulo.setText ("Inhabilitar " + u.getNombre () + "?");

			view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					u.inhabilitar ();
					dialog.dismiss();
				}
			});

			dialog.setContentView(view);
			dialog.show();
		}
	}
}

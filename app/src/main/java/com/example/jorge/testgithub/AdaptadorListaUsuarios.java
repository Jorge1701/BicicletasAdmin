package com.example.jorge.testgithub;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jorge.testgithub.Clases.Usuario;

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
    	@BindView (R.id.tvNombre)
		TextView tvNombre;
		@BindView (R.id.btnHabilitar)
		public Button btnHabilitar;
		@BindView (R.id.btnInhabilitar)
		public Button btnInhabilitar;
		@BindView (R.id.barraProgreso)
		public ProgressBar barraProgreso;
		CardView cardView;

		private Context mContext;

		public UsuarioViewHolder (View itemView) {
			super (itemView);
			ButterKnife.bind (this, itemView);
			cardView = itemView.findViewById (R.id.item_lista_usuario);
			mContext = itemView.getContext ();
		}

		public void bindUsuario (final Usuario u) {
			tvNombre.setText (u.getNombre ());
			// TODO: Cargar imagen del usuario
			boolean activo = u.isActivado() != 0;

			barraProgreso.setVisibility (View.GONE);

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

			((TextView) view.findViewById(R.id.tvTitulo)).setText ("Habilitar " + u.getNombre () + "?");

			view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					barraProgreso.setVisibility (View.VISIBLE);
					btnHabilitar.setVisibility (View.GONE);
					u.habilitar (UsuarioViewHolder.this);
					dialog.dismiss();
				}
			});

			dialog.setContentView(view);
			dialog.show();
		}

		private void Inhabilitar (final Usuario u) {
			final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
			View view = LayoutInflater.from(mContext).inflate(R.layout.custom_alert, null);

			((TextView) view.findViewById(R.id.tvTitulo)).setText ("Inhabilitar " + u.getNombre () + "?");

			view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					barraProgreso.setVisibility (View.VISIBLE);
					btnInhabilitar.setVisibility (View.GONE);
					u.inhabilitar (UsuarioViewHolder.this);
					dialog.dismiss();
				}
			});

			dialog.setContentView(view);
			dialog.show();
		}
	}
}

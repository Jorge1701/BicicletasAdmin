package com.example.jorge.testgithub;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jorge.testgithub.Clases.ComentarioIncidencia;
import com.example.jorge.testgithub.Clases.Incidencia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorListaIncidencias extends RecyclerView.Adapter<AdaptadorListaIncidencias.IncidenciaViewHolder> {

	private Context context;
	private List<Incidencia> incidencias;

	public AdaptadorListaIncidencias (Context context, List<Incidencia> incidencias) {
		this.context = context;
		this.incidencias = incidencias;
	}

	@Override
	public AdaptadorListaIncidencias.IncidenciaViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
		View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_incidencia, parent, false);
		IncidenciaViewHolder vh = new IncidenciaViewHolder (v);
		return vh;
	}

	@Override
	public void onBindViewHolder(AdaptadorListaIncidencias.IncidenciaViewHolder holder, int position) {
		YoYo.with(Techniques.ZoomIn).duration(500).playOn(holder.cardView);
		holder.bindIncidencia(incidencias.get(position));
	}

	@Override
	public int getItemCount() {
		return incidencias.size();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onViewAttachedToWindow(AdaptadorListaIncidencias.IncidenciaViewHolder usuarioViewHolder){
		super.onViewAttachedToWindow(usuarioViewHolder);
		//animateCircularReveal(paradaViewHolder.itemView);
	}

	public class IncidenciaViewHolder extends RecyclerView.ViewHolder {

		@BindView (R.id.tvNombreUsuario)
		TextView tvNombreUsuario;
		@BindView (R.id.btnEstado)
		Button estado;
		@BindView (R.id.tvNombreAsignado)
		TextView tvAsignado;
		@BindView (R.id.tvParadaIncidencia)
		TextView tvParadaIncidencia;
		@BindView (R.id.tvDescripcion)
		TextView tvDescripcion;
		@BindView (R.id.ivSendComentar)
		ImageView ivSendComentar;
		@BindView (R.id.llComentarios)
		LinearLayout llComentarios;
		@BindView (R.id.tvTituloComentarios)
		TextView tvTituloComentarios;
		@BindView (R.id.etComentar)
		EditText etComentar;
		@BindView (R.id.enviarProgressBar)
		ProgressBar enviarProgressBar;

		@BindView (R.id.cabecera)
		LinearLayout cabecera;
		@BindView (R.id.msjExpandir)
		LinearLayout msjExpandir;
		@BindView (R.id.contenido)
		LinearLayout contenido;

		CardView cardView;

		private Context context;
		private Incidencia i;

		private boolean abierto = false;

		public IncidenciaViewHolder (View v) {
			super (v);
			ButterKnife.bind (this, v);
			cardView = itemView.findViewById (R.id.item_lista_usuario);
			context = v.getContext ();
		}

		public void bindIncidencia (final Incidencia i) {
			this.i = i;
			// TODO: Cargar imagen del usuario
			tvNombreUsuario.setText (i.getUsuario ());

			cargarEstado ();

			estado.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (i.getEstado ()) {
						case 0:
							Asignar (i);
							break;
						case 1:
							Resolver (i);
							break;
					}
				}
			});

			tvParadaIncidencia.setText (i.getParada () == null ? "---" : i.getParada ());
			tvDescripcion.setText (i.getComentario ());

			cargarComentarios ();

			ivSendComentar.setOnClickListener (new View.OnClickListener () {
				@Override
				public void onClick(View v2) {
					if (etComentar.getText ().toString ().equals (""))
						return;

					ivSendComentar.setVisibility (View.GONE);
					enviarProgressBar.setVisibility (View.VISIBLE);

					SharedPreferences sp = context.getSharedPreferences("usuario_logueado", Context.MODE_PRIVATE);
					String usuario = sp.getString("usuario", null);
					i.comentar (IncidenciaViewHolder.this, usuario, etComentar.getText ().toString ());
				}
			});

			cabecera.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (abierto) {
						com.example.jorge.testgithub.Util.Animation.colapsar (contenido);
						msjExpandir.setVisibility(View.VISIBLE);
					} else {
						com.example.jorge.testgithub.Util.Animation.expandir (contenido);
						msjExpandir.setVisibility(View.GONE);
					}
					abierto = !abierto;
				}
			});
		}

		private void cargarEstado () {
			switch (i.getEstado ()) {
				case 0:
					tvAsignado.setText ("");
					estado.setTextColor (0xFFFF4444);
					estado.setText ("ABIERTA");
					break;
				case 1:
					// TODO: tvAsignado.setText (i.getAsignado ());
					estado.setTextColor (0xFFFF9D00);
					estado.setText ("ASIGNADA");
					break;
				case 2:
					// TODO: tvAsignado.setText (i.getAsignado ());
					estado.setTextColor (0xFF35D002);
					estado.setText ("RESUELTA");
					break;
			}
		}

		public void cargarComentarios () {
			if (i.getComentarioIncidencias () == null || i.getComentarioIncidencias ().size () == 0) {
				tvTituloComentarios.setVisibility (View.GONE);
				llComentarios.setVisibility (View.GONE);
			} else {
				if (llComentarios.getChildCount () > 0)
					llComentarios.removeAllViews ();

				tvTituloComentarios.setVisibility (View.VISIBLE);
				llComentarios.setVisibility (View.VISIBLE);

				for (ComentarioIncidencia c: i.getComentarioIncidencias ()) {
					View v = LayoutInflater.from (context).inflate (R.layout.item_comentario, null, false);
					((TextView) v.findViewById (R.id.tvNombreAdmin)).setText (c.getAdmin ());
					((TextView) v.findViewById (R.id.tvComentario)).setText (c.getComentario ());
					llComentarios.addView (v);
				}
			}
			etComentar.setText ("");
			enviarProgressBar.setVisibility (View.GONE);
			ivSendComentar.setVisibility (View.VISIBLE);
		}

		public void errorAlComentar () {
			etComentar.setText ("");
			enviarProgressBar.setVisibility (View.GONE);
			ivSendComentar.setVisibility (View.VISIBLE);
			Toast.makeText(context, "No se pudo enviar el comentario", Toast.LENGTH_SHORT).show();
		}

		private void Asignar (final Incidencia i) {
			final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
			View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_alert, null);

			((TextView) view.findViewById (R.id.tvTitulo)).setText ("Asignar Incidencia?");
			final EditText etNombreAsigando = ((EditText) view.findViewById (R.id.etNombreAsignado));

			view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					i.setEstado (1);
					// TODO: i.setAsignado (etNombreAsigando.getText ().toString ());
					cargarEstado ();
					dialog.dismiss();
				}
			});

			dialog.setContentView(view);
			dialog.show();
		}

		private void Resolver (final Incidencia i) {
			final Dialog dialog = new Dialog (context, android.R.style.Theme_Translucent_NoTitleBar);
			View view = LayoutInflater.from (context).inflate (R.layout.custom_alert, null);

			((TextView) view.findViewById (R.id.tvTitulo)).setText ("Resolver Incidencia?");

			view.findViewById (R.id.btnCancelar).setOnClickListener (new View.OnClickListener () {
				@Override
				public void onClick (View v) {
					dialog.dismiss ();
				}
			});

			view.findViewById (R.id.btnAceptar).setOnClickListener (new View.OnClickListener () {
				@Override
				public void onClick(View v) {
					i.setEstado (2);
					cargarEstado ();
					dialog.dismiss ();
				}
			});

			dialog.setContentView (view);
			dialog.show ();
		}
	}
}
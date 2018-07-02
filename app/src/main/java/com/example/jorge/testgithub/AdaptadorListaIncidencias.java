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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.RespuestaAdministradores;
import com.example.jorge.testgithub.Clases.Administrador;
import com.example.jorge.testgithub.Clases.ComentarioIncidencia;
import com.example.jorge.testgithub.Clases.Incidencia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

		@BindView (R.id.llEstadoAsignado)
		public LinearLayout llEstadoAsignado;
		@BindView (R.id.estadoProgressBar)
		public ProgressBar estadoProgressBar;

		CardView cardView;

		public Context context;
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
			tvAsignado.setText (i.getAdmin ());

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

		public void cargarEstado () {
			switch (i.getEstado ()) {
				case 0:
					tvAsignado.setText ("");
					estado.setTextColor (0xFFFF4444);
					estado.setText ("ABIERTA");
					break;
				case 1:
					tvAsignado.setText (i.getAdmin ());
					estado.setTextColor (0xFFFF9D00);
					estado.setText ("ASIGNADA");
					break;
				case 2:
					tvAsignado.setText (i.getAdmin ());
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
			final View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_alert, null);

			final Spinner spinner = (Spinner) view.findViewById (R.id.spinner);
			final LinearLayout llProgressBar = (LinearLayout) view.findViewById (R.id.llProgressBar);
			BDInterface bd = BDCliente.getClient().create(BDInterface.class);
			Call<RespuestaAdministradores> call = bd.obtenerAdministradores();
			call.enqueue(new Callback<RespuestaAdministradores>() {
				@Override
				public void onResponse(Call<RespuestaAdministradores> call, Response<RespuestaAdministradores> response) {
					cargarAdministradores(spinner, llProgressBar, response.body().getAdministradores());
					view.findViewById(R.id.btnAceptar).setEnabled(true);
				}

				@Override
				public void onFailure(Call<RespuestaAdministradores> call, Throwable t) {
					Toast.makeText(context, "Error al cargar los administradores", Toast.LENGTH_SHORT).show();
					Log.d("ASD", t.getMessage());
				}
			});

			((TextView) view.findViewById (R.id.tvTitulo)).setText ("Asignar Incidencia?");

			view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			view.findViewById(R.id.btnAceptar).setEnabled(false);
			view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					llEstadoAsignado.setVisibility(View.GONE);
					estadoProgressBar.setVisibility(View.VISIBLE);
					i.cambiarEstado (IncidenciaViewHolder.this, spinner.getSelectedItem ().toString (), 1);
					dialog.dismiss();
				}
			});

			dialog.setContentView(view);
			dialog.show();
		}

		private void cargarAdministradores (Spinner s, LinearLayout ll, List<Administrador> admins) {
			List<String> ads = new ArrayList<>();
			for (Administrador a : admins)
				ads.add(a.getEmail());

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ads);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			s.setAdapter(adapter);

			ll.setVisibility(View.GONE);
			s.setVisibility(View.VISIBLE);
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
					llEstadoAsignado.setVisibility(View.GONE);
					estadoProgressBar.setVisibility(View.VISIBLE);
					i.cambiarEstado (IncidenciaViewHolder.this, null, 2);
					dialog.dismiss();
				}
			});

			dialog.setContentView (view);
			dialog.show ();
		}
	}
}
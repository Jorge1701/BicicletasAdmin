package com.example.jorge.testgithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.Clases.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLogin extends AppCompatActivity {

    @BindView(R.id.etUsuario)
    EditText etUsuario;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.cbRecordar)
    CheckBox cbRecordar;
    @BindView(R.id.etUsuarioError)
    TextInputLayout etUsuarioError;
    @BindView(R.id.etPasswordError)
    TextInputLayout etPasswordError;
    @BindView(R.id.pbISesion)
    ProgressBar pbISesion;
    @BindView(R.id.btnIniciarSesion)
    Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        ButterKnife.bind(this);
    }

    private void verificarUsuario(final String u, final String password) {

        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<Respuesta> call = bd.login("Parametro no utilizado", password, u);
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.body().getCodigo().equals("1")) {
                    if (cbRecordar.isChecked())
                        recordarUsuario(u, password);

                    cargarInicioAdmin(u, password);
                } else
                    Toast.makeText(AdminLogin.this, getString(R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
                btnIniciarSesion.setVisibility(View.VISIBLE);
                pbISesion.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(AdminLogin.this, "Error de conexión con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                btnIniciarSesion.setVisibility(View.VISIBLE);
                pbISesion.setVisibility(View.GONE);

            }
        });

    }

    public void IniciarSesion(View v) {
        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        etUsuarioError.setError(null);
        etPasswordError.setError(null);

        if (usuario.equals("")) {
            //Toast.makeText (this, getResources ().getString (R.string.falta_usuario_contraseña), Toast.LENGTH_LONG).show ();
            etUsuarioError.setError("Usuario incorrecto");
            return;
        } else if (password.equals("")) {
            etPasswordError.setError("Contraseña incorrecta");
            return;
        } else {
            btnIniciarSesion.setVisibility(View.GONE);
            pbISesion.setVisibility(View.VISIBLE);
            verificarUsuario(usuario, password);
        }
    }

    private void recordarUsuario(String usuario, String password) {
        SharedPreferences sp = getSharedPreferences("usuario_guardado", MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("usuario", usuario);
        et.putString("password", password);
        et.commit();
    }

    private void cargarInicioAdmin(String usuario, String password) {
        SharedPreferences sp = getSharedPreferences("usuario_guardado_temp", MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("usuario", usuario);
        et.putString("password", password);
        et.commit();

        Intent i = new Intent(AdminLogin.this, MenuAdmin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}

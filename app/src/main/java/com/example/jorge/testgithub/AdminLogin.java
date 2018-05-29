package com.example.jorge.testgithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jorge.testgithub.Util.Login;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminLogin extends AppCompatActivity implements Login {

    @BindView (R.id.etUsuario)
    EditText etUsuario;
    @BindView (R.id.etPassword)
    EditText etPassword;
    @BindView (R.id.cbRecordar)
    CheckBox cbRecordar;
    @BindView(R.id.etUsuarioError)
    TextInputLayout etUsuarioError;
    @BindView(R.id.etPasswordError)
    TextInputLayout etPasswordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        ButterKnife.bind (this);
    }

    @Override
    public void verificarLogin (boolean verificado, String usuario, String password) {
        if (verificado) {
            if (cbRecordar.isChecked())
                recordarUsuario(usuario, password);

            cargarInicioAdmin (usuario, password);
        } else
            Toast.makeText(this, getResources ().getString (R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
    }

    public void IniciarSesion (View v) {
        String usuario = etUsuario.getText ().toString ().trim ();
        String password = etPassword.getText ().toString ().trim ();

        etUsuarioError.setError(null);
        etPasswordError.setError(null);

        if (usuario.equals ("")) {
            //Toast.makeText (this, getResources ().getString (R.string.falta_usuario_contraseña), Toast.LENGTH_LONG).show ();
           etUsuarioError.setError("usuario incorrecto");
            return;
        }else if(password.equals ("")){
            etPasswordError.setError("Contraseña incorrecta");
            return;
        }else
            Usuario.verificarUsuario (this, usuario, password);
    }

    private void recordarUsuario (String usuario, String password) {
        SharedPreferences sp = getSharedPreferences ("usuario_guardado", MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit ();
        et.putString ("usuario", usuario);
        et.putString ("password", password);
        et.commit ();
    }

    private void cargarInicioAdmin (String usuario, String password) {
		SharedPreferences sp = getSharedPreferences ("usuario_guardado_temp", MODE_PRIVATE);
		SharedPreferences.Editor et = sp.edit ();
		et.putString ("usuario", usuario);
		et.putString ("password", password);
		et.commit ();

        Intent i = new Intent(AdminLogin.this, MenuAdmin.class);
        i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity (i);
    }
}

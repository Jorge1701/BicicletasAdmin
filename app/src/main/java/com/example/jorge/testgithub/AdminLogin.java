package com.example.jorge.testgithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminLogin extends AppCompatActivity {

    @BindView (R.id.etUsuario)
    EditText etUsuario;
    @BindView (R.id.etPassword)
    EditText etPassword;
    @BindView (R.id.cbRecordar)
    CheckBox cbRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        // Se cargan los datos del usuario guardado
        SharedPreferences sp = getSharedPreferences ("usuario_guardado", MODE_PRIVATE);
        String usuario = sp.getString ("usuario", null);
        String password = sp.getString ("password", null);

        // Si hay datos se verifica que sean validos
        if (usuario != null && password != null) {
            if (verificarUsuario (usuario, password))
                // Si son validos se va a la pantalla inicial
                cargarInicioAdmin ();
            else {
                // En caso de que el usuario fuera eliminado mientras tenia la aplicacion cerrada se borran los datos y se muestra un mensaje
                SharedPreferences.Editor ed = sp.edit();
                ed.clear();
                ed.commit();
                Toast.makeText(this, getResources().getString(R.string.login_incorrecto), Toast.LENGTH_LONG).show();
            }
        }

        ButterKnife.bind (this);
    }

    public void IniciarSesion (View v) {
        String usuario = etUsuario.getText ().toString ().trim ();
        String password = etPassword.getText ().toString ().trim ();

        if (usuario.equals ("") || password.equals ("")) {
            Toast.makeText (this, getResources ().getString (R.string.falta_usuario_contraseña), Toast.LENGTH_LONG).show ();
            return;
        }

        if (verificarUsuario (usuario, password)) {
            if (cbRecordar.isChecked())
                recordarUsuario(usuario, password);

            cargarInicioAdmin ();
        } else
            Toast.makeText(this, getResources ().getString (R.string.login_incorrecto), Toast.LENGTH_SHORT).show();
    }

    private boolean verificarUsuario (String usuario, String password) {
        // TODO: Verificar usuario con la base de datos
        return true;
    }

    private void recordarUsuario (String usuario, String password) {
        SharedPreferences sp = getSharedPreferences ("usuario_guardado", MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit ();
        et.putString ("usuario", usuario);
        et.putString ("password", password);
        et.commit ();
    }

    private void cargarInicioAdmin () {
        Intent i = new Intent (AdminLogin.this, ListadoUsuarios.class);
        i.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity (i);

    }
}

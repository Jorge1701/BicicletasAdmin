package com.example.jorge.testgithub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jorge.testgithub.BD.BDCliente;
import com.example.jorge.testgithub.BD.BDInterface;
import com.example.jorge.testgithub.BD.Respuesta;
import com.example.jorge.testgithub.Clases.Usuario;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UsuariosListado.OnFragmentInteractionListener, ParadasListado.OnFragmentInteractionListener, BicisListado.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Administrador");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_usuarios));

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        ButterKnife.bind(this);

        // Se cargan los datos del usuario guardado
        SharedPreferences sp = getSharedPreferences("usuario_guardado", MODE_PRIVATE);
        String usuario = sp.getString("usuario", null);
        String password = sp.getString("password", null);

        SharedPreferences sp_tmp = getSharedPreferences("usuario_guardado_temp", MODE_PRIVATE);
        String usuario_tmp = sp_tmp.getString("usuario", null);
        String password_tmp = sp_tmp.getString("password", null);

        String u = "";
        String p = "";

        if (usuario_tmp != null && password_tmp != null) {
            u = usuario_tmp;
            p = password_tmp;
        } else if (usuario != null && password != null) {
            u = usuario;
            p = password;
        }

        // Si hay datos se verifica que sean validos
        if (u.isEmpty() && p.isEmpty()) {
            // En caso de que el usuario fuera eliminado mientras tenia la aplicacion cerrada se borran los datos y se muestra un mensaje
            cerrarSesion();
        } else
            verificarUsuario(u, p);
    }

    private void verificarUsuario(String u, String password) {

        BDInterface bd = BDCliente.getClient().create(BDInterface.class);
        Call<Respuesta> call = bd.login("Andate a cagar", password, u);
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                Log.d("ASD", response.body().getCodigo());
                if (!response.body().getCodigo().equals("1")) {
                    cerrarSesion();
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("FALLO", t.getMessage());
            }
        });

    }

    private void cerrarSesion() {
        SharedPreferences sp = getSharedPreferences("usuario_guardado", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.commit();

        Intent i = new Intent(MenuAdmin.this, AdminLogin.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        SharedPreferences sp = getSharedPreferences("usuario_guardado_temp", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.commit();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_ver_mapa:
                fragment = new AgregarParada();
                //fragment = new MapaCalor();
                fragmentTransaction = true;
                break;
            case R.id.nav_editar_mapa:
                fragment = new EditarParada();
                fragmentTransaction = true;
                break;
            case R.id.nav_usuarios:
                fragment = new UsuariosListado();
                fragmentTransaction = true;
                break;
            case R.id.nav_paradas:
                fragment = new ParadasListado();
                fragmentTransaction = true;
                break;
            case R.id.nav_paradas_alquileres:
                fragment = new ParadasListado();
                ((ParadasListado) fragment).setAlquileres(true);
                fragmentTransaction = true;
                break;
            case R.id.nav_bicicletas:
                fragment = new BicisListado();
                fragmentTransaction = true;
                break;
            case R.id.nav_incidencias:
                fragment = new IncidenciaListado();
                fragmentTransaction = true;
                break;
            case R.id.nav_cerrar_sesion:
                cerrarSesion();
                break;
        }

        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenido_seleccionado, fragment)
                    .commit();
            item.setChecked(true);
            getSupportActionBar().setTitle("  " + item.getTitle());
            Drawable icon = item.getIcon();
            icon.mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setIcon(icon);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

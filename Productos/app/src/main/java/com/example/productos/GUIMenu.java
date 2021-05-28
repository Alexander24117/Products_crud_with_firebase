package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class GUIMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_u_i_menu);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarr);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.agregar:

                intent = new Intent(this, Agregar.class);
                startActivity(intent);
                return true;
            case R.id.buscar:
                intent = new Intent(this, Buscar.class);
                startActivity(intent);
                return true;
            case R.id.actualizar:
                intent = new Intent(this, Modificar.class);
                startActivity(intent);
                return true;
            case R.id.eliminar:
                intent = new Intent(this, Eliminar.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void btn_add(View view) {
        Intent intent = new Intent(this, Agregar.class);
        startActivity(intent);
    }

    public void btn_buscar(View view) {
        Intent intent = new Intent(this, Buscar.class);
        startActivity(intent);
    }

    public void btn_listar(View view) {
        Intent intent = new Intent(this, Listar.class);
        startActivity(intent);
    }

    public void btn_eliminar(View view) {
        Intent intent = new Intent(this, Eliminar.class);
        startActivity(intent);
    }

    public void btn_modificar(View view) {
        Intent intent = new Intent(this, Modificar.class);
        startActivity(intent);
    }

    public void btn_consultar(View view) {
        Intent intent = new Intent(this, Consultar.class);
        startActivity(intent);
    }

}
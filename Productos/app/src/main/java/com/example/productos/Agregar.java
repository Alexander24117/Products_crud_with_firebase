package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.example.productos.estructural.Categoria;
import com.example.productos.estructural.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;


public class Agregar extends AppCompatActivity {

    private TextView txtCodigo;
    private TextView txtNombre;
    private Spinner txtCategoria;
    private TextView txtPrecioC;
    private TextView txtIva;
    private TextView txtPrecioV;
    private TextView txtFechaV;
    private TextView txtCantidad;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbarr);
        txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtCategoria = (Spinner) findViewById(R.id.txtCategoria);
        txtPrecioC = (TextView) findViewById(R.id.txtPrecioC);
        txtIva = (TextView) findViewById(R.id.txtIva);
        txtPrecioV = (TextView) findViewById(R.id.txtPrecioV);
        txtFechaV = (TextView) findViewById(R.id.txtFechaV);
        txtCantidad = (TextView) findViewById(R.id.txtCantidad);

        db = FirebaseFirestore.getInstance();

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
            case R.id.menu:
                intent = new Intent(this, GUIMenu.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void fechaOnClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "-" + month + "-" + year;
                txtFechaV.setText(fecha);
            }
        }, anio, mes, dia);
        pickerDialog.show();
    }

    public void btnAdicionarProducto_Click(View view) {
        String strCodigo, strNombre, strCategoria, strPrecioCompra, strIva, strPrecioVenta, strFechaVencimiento, strCantidad;
        Producto producto;
        int codigo;
        String nombre;
        String categoria;
        double precioCompra;
        double iva;
        double precioVenta;
        Date fechaVencimiento;
        int cantidad;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        strCodigo = txtCodigo.getText().toString();
        strNombre = txtNombre.getText().toString();
        categoria = txtCategoria.getSelectedItem().toString();
        strPrecioCompra = txtPrecioC.getText().toString();
        strIva = txtIva.getText().toString();
        strPrecioVenta = txtPrecioV.getText().toString();
        strFechaVencimiento = txtFechaV.getText().toString();
        strCantidad = txtCantidad.getText().toString();


        if (strCodigo.isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        if (strNombre == null || strNombre.equals("")) {
            Toast.makeText(this, "El nombre no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        if (categoria== null || strNombre.equals("")) {
            Toast.makeText(this, "Debe seleccionar una categoría", Toast.LENGTH_LONG).show();
            return;
        }
        if (strPrecioCompra == null || strPrecioCompra.equals("")) {
            Toast.makeText(this, "El Precio de compra no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        if (strIva == null || strIva.equals("")) {
            Toast.makeText(this, "El Iva no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        if (strPrecioVenta == null || strPrecioVenta.equals("")) {
            Toast.makeText(this, "el precio de venta no debe estar vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        if (strFechaVencimiento == null || strFechaVencimiento.equals("")) {
            Toast.makeText(this, "la fecha de vencimiento no debe estar vacía!", Toast.LENGTH_LONG).show();
            return;
        }

        if (strCantidad == null || strCantidad.equals("")) {
            Toast.makeText(this, "La Cantidad no debe estar vacía!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            codigo = Integer.parseInt(strCodigo);
        } catch (Exception e) {
            Toast.makeText(this, "El Código NO es un número válido!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            precioCompra = Double.parseDouble(strPrecioCompra);
        } catch (Exception e) {
            Toast.makeText(this, "el precio de compra NO es un número válido!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            iva = Double.parseDouble(strIva);
        } catch (Exception e) {
            Toast.makeText(this, "el Iva NO es un número válido!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            precioVenta = Double.parseDouble(strPrecioVenta);
        } catch (Exception e) {
            Toast.makeText(this, "el precio de venta NO es un número válido!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            fechaVencimiento = sdf.parse(strFechaVencimiento);
        } catch (Exception e) {
            Toast.makeText(this, "La fehca NO es válida!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            cantidad = Integer.parseInt(strCantidad);
        } catch (Exception e) {
            Toast.makeText(this, "La cantidad NO es un número válido!", Toast.LENGTH_LONG).show();
            return;
        }

        producto = new Producto(codigo, strNombre, categoria, precioCompra, iva, precioVenta, fechaVencimiento, cantidad, Producto.STATUS_AC);

        db.collection(Producto.NAME_COLLECTION)

                        .document(String.valueOf(producto.getCodigo()))
                        .set(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Agregar.this , "Producto adicionado" , Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Agregar.this , "Producto No adicionado: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


}
    }

   

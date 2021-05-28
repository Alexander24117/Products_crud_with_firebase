package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.example.productos.estructural.Categoria;
import com.example.productos.estructural.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class Modificar extends AppCompatActivity {
    private TextView txtAmod;

    private TextView txtnom;
    private Spinner txtCat;
    private TextView txtPrecC;
    private TextView txtIva;
    private TextView txtPrecV;
    private TextView txtFecha;
    private TextView txtCant;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbarr);
        txtAmod = (TextView) findViewById(R.id.txtAmod);
        txtnom = (TextView) findViewById(R.id.txtCambnom);
        txtCat = (Spinner) findViewById(R.id.txtCambCat);
        txtPrecC = (TextView) findViewById(R.id.txtCambPrecC);
        txtIva = (TextView) findViewById(R.id.txtCambIva);
        txtPrecV = (TextView) findViewById(R.id.txtCambprecV);
        txtFecha = (TextView) findViewById(R.id.txtCambFecha);
        txtCant = (TextView) findViewById(R.id.txtCambcant);

        db = FirebaseFirestore.getInstance();

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
                txtFecha.setText(fecha);
            }
        }, anio, mes, dia);
        pickerDialog.show();
    }


    public void btnBuscarOnclick(View view) {
        int txtBust;
        String strCodigo = txtAmod.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtAmod.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        txtBust = Integer.parseInt(strCodigo);
        System.out.println(txtBust);

        DocumentReference docRef = db.collection(Producto.NAME_COLLECTION).document(strCodigo);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Producto producto = documentSnapshot.toObject(Producto.class);

                String  precioCompra, iva, precioVenta, fechaV, cantida, cat;
                precioCompra = String.valueOf(producto.getPrecioCompra());
                cat = producto.getCategoria();
                iva = String.valueOf(producto.getIva());
                precioVenta = String.valueOf(producto.getPrecioVenta());
                fechaV = dateFormat.format(producto.getFechaVencimiento());
                cantida = String.valueOf(producto.getCantidad());
                String nombre = producto.getNombre();
                txtnom.setText(nombre);
                for (int i=0;i<txtCat.getCount();i++){
                    if(txtCat.getSelectedItem().toString().equals(producto.getCategoria())){
                        txtCat.setSelection(i);
                    }
                }
                txtPrecC.setText(precioCompra);
                txtIva.setText(iva);
                txtPrecV.setText(precioVenta);
                txtFecha.setText(fechaV);
                txtCant.setText(cantida);
                txtnom.requestFocus();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Modificar.this, "No se encontro el producto "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void actualizar(View view) {
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

        strNombre = txtnom.getText().toString();
        categoria = txtCat.getSelectedItem().toString();
        strCodigo = txtAmod.getText().toString();
        strPrecioCompra = txtPrecC.getText().toString();
        strIva = txtIva.getText().toString();
        strPrecioVenta = txtPrecV.getText().toString();
        strFechaVencimiento = txtFecha.getText().toString();
        strCantidad = txtCant.getText().toString();


        if (strCodigo.isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        if (strNombre.equals("")) {
            Toast.makeText(this, "El nombre no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }
        if (strNombre.equals("")) {
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

        db.collection(Producto.NAME_COLLECTION).document(strCodigo)
                .update(
                        "nombre",producto.getNombre(),
                        "categoria",producto.getCategoria(),
                        "precioCompra",producto.getPrecioCompra(),
                        "iva",producto.getIva(),
                        "precioVenta",producto.getPrecioVenta(),
                        "fechaVencimiento",producto.getFechaVencimiento(),
                        "cantidad",producto.getCantidad()
                );
    }
}

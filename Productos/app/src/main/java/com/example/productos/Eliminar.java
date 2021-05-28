package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.estructural.Categoria;
import com.example.productos.estructural.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Eliminar extends AppCompatActivity {
    private TextView txtAeli;
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
        setContentView(R.layout.activity_eliminar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbarr);
        txtAeli = (TextView) findViewById(R.id.txtAeli);

        txtnom = (TextView) findViewById(R.id.txtCambnom3);
        txtCat = (Spinner) findViewById(R.id.txtCambCat3);
        txtPrecC = (TextView) findViewById(R.id.txtCambPrecC3);
        txtIva = (TextView) findViewById(R.id.txtCambIva3);
        txtPrecV = (TextView) findViewById(R.id.txtCambprecV3);
        txtFecha = (TextView) findViewById(R.id.txtCambFecha3);
        txtCant = (TextView) findViewById(R.id.txtCambcant);

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
                txtFecha.setText(fecha);
            }
        }, anio, mes, dia);
        pickerDialog.show();
    }

    public void btnBuscarOnclick(View view) {
        int txtBust;
        String strCodigo = txtAeli.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtAeli.getText().toString().isEmpty()) {
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
                Toast.makeText(Eliminar.this, "No se encontro el producto "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    public void btnEliminar_Click(View view) {
        int txtBust;
        String strCodigo = txtAeli.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtAeli.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        txtBust = Integer.parseInt(strCodigo);
        System.out.println(txtBust);

        db.collection(Producto.NAME_COLLECTION).document(strCodigo)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Eliminar.this, "se elimino el producto!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Eliminar.this, "No se pudo eliminar: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void btnEliminar_logico(View view){
        int txtBust;
        String strCodigo = txtAeli.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtAeli.getText().toString().isEmpty()) {
            Toast.makeText(this, "El código no debe ser vacío!", Toast.LENGTH_LONG).show();
            return;
        }

        txtBust = Integer.parseInt(strCodigo);
        System.out.println(txtBust);
        db.collection(Producto.NAME_COLLECTION)
                .document(strCodigo)
                .update("status",Producto.STATUS_IN)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Eliminar.this, "se elimino el producto!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Eliminar.this, "no se elimino el producto! " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.estructural.Producto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;


public class Buscar extends AppCompatActivity {
    private TextView txtBuscq;
    private static TextView txtBuscado;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbarr);
        txtBuscq = (TextView) findViewById(R.id.txtBuscarIn);
        txtBuscado = findViewById(R.id.txtBuscado);

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
    public static void llenarbusqueda(Producto producto){
        String cadena;
        txtBuscado.clearComposingText();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        txtBuscado.setText("");
        cadena = "Código: " + producto.getCodigo() + "\n"
                + "Nombre: " + producto.getNombre() + "\n"
                + "Categoria: " + producto.getCategoria() + "\n"
                + "PrecioCompra: " + producto.getPrecioCompra() + "\n"
                + "Iva: " + producto.getIva() + "\n"
                + "Precioventa: " + producto.getPrecioVenta() + "\n"
                + "FechaVencimiento: " + dateFormat.format(producto.getFechaVencimiento()) + "\n"
                + "Cantidad: " + producto.getCantidad() + "\n";
        txtBuscado.append(cadena);

    }

    public void btnBuscar_Click(View view) {
        int txtBust;
        String strCodigo = txtBuscq.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (txtBuscq.getText().toString().isEmpty()) {
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
                Buscar.llenarbusqueda(producto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Buscar.this, "No se encontro el producto "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}

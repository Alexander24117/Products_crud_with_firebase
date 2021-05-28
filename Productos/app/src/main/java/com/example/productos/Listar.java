package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import com.example.productos.estructural.Producto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.List;
import java.util.Objects;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;


public class Listar extends AppCompatActivity {
    private static ListView txtListar;
    private FirebaseFirestore db;
    private String strProductos;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        Toolbar toolbarr =  findViewById(R.id.toolbar5);
        setSupportActionBar(toolbarr);
        txtListar =  findViewById(R.id.txtListaS);
        search = (SearchView) findViewById(R.id.busqueda);
        db = FirebaseFirestore.getInstance();
        strProductos = "";

        }


    public void cargar (View view)  {


        db.collection(Producto.NAME_COLLECTION).whereEqualTo("status","AC")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Producto>productos = new ArrayList<>();
                            for(QueryDocumentSnapshot document: task.getResult()){
                                productos.add(document.toObject(Producto.class));
                            }
                            ArrayList<String>listado = new ArrayList<String>();
                            for (int i = 0; i <productos.size(); i++) {
                                if(productos.get(i).getStatus().equals(Producto.STATUS_AC)){
                                    listado.add(productos.get(i).getNombre());
                                }
                            }


                            String Datos[] = listado.toArray(new String[listado.size()]);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String >(Listar.this , android.R.layout.simple_list_item_1 , Datos);

                            txtListar.setAdapter(adapter);
                            txtListar.setTextFilterEnabled(true);
                            txtListar.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String cadena;
                                    Producto producto = productos.get(position);
                                    cadena = "Código: " + producto.getCodigo() + "\n"
                                            + "Nombre: " + producto.getNombre() + "\n"
                                            + "Categoria: " + producto.getCategoria() + "\n"
                                            + "PrecioCompra: " + producto.getPrecioCompra() + "\n"
                                            + "Iva: " + producto.getIva() + "\n"
                                            + "Precioventa: " + producto.getPrecioVenta() + "\n"
                                            + "FechaVencimiento: " + dateFormat.format(producto.getFechaVencimiento()) + "\n"
                                            + "Cantidad: " + producto.getCantidad() + "\n";
                                   AlertDialog.Builder builder = new AlertDialog.Builder(Listar.this);
                                   builder.setMessage(cadena).setTitle(producto.getNombre());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button+
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                                }
                            });


                            Toast.makeText(Listar.this, "carga: "+ productos.size() , Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Listar.this, "Error carga: "+ Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void cargarPorEstados(View view){
        db.collection(Producto.NAME_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Producto>productos = new ArrayList<>();
                            for(QueryDocumentSnapshot document: task.getResult()){
                                productos.add(document.toObject(Producto.class));
                            }
                            ArrayList<String>listado = new ArrayList<String>();
                            for (int i = 0; i <productos.size(); i++) {

                                    listado.add(productos.get(i).getNombre()+" - "+ productos.get(i).getStatus());

                            }


                            String Datos[] = listado.toArray(new String[listado.size()]);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String >(Listar.this , android.R.layout.simple_list_item_1 , Datos);

                            txtListar.setAdapter(adapter);
                            txtListar.setTextFilterEnabled(true);
                            txtListar.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    String cadena;
                                    Producto producto = productos.get(position);
                                    cadena = "Código: " + producto.getCodigo() + "\n"
                                            + "Nombre: " + producto.getNombre() + "\n"
                                            + "Categoria: " + producto.getCategoria() + "\n"
                                            + "PrecioCompra: " + producto.getPrecioCompra() + "\n"
                                            + "Iva: " + producto.getIva() + "\n"
                                            + "Precioventa: " + producto.getPrecioVenta() + "\n"
                                            + "FechaVencimiento: " + dateFormat.format(producto.getFechaVencimiento()) + "\n"
                                            + "Cantidad: " + producto.getCantidad() + "\n";
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Listar.this);
                                    builder.setMessage(cadena).setTitle(producto.getNombre());
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button+
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                            search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    adapter.getFilter().filter(query);
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapter.getFilter().filter(newText);
                                    return false;
                                }
                            });


                            Toast.makeText(Listar.this, "carga: "+ productos.size() , Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Listar.this, "Error carga: "+ Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    }












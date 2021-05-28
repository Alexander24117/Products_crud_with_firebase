package com.example.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productos.estructural.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Consultar extends AppCompatActivity {

    private static TextView txtRegistrados;
    private static TextView txtTotal;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        txtRegistrados = findViewById(R.id.txtProductosR);
        txtTotal = findViewById(R.id.txtTotal);
        db = FirebaseFirestore.getInstance();

    }

    public  static void setText(int prod,int total){
        txtRegistrados.setText(""+prod);
        txtTotal.setText(""+total);
    }
    public void onClick(View view){
        db.collection(Producto.NAME_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        List<Producto> productos = new ArrayList<>();
                        int total = 0;
                        int i =0;
                        for(QueryDocumentSnapshot document: task.getResult()){
                            productos.add(document.toObject(Producto.class));
                            total+=productos.get(i).getCantidad();
                            i++;
                        }
                        Consultar.setText(productos.size(),total);

                    }
                });

    }
}

package com.example.loginbl;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CultivoAdapter.OnCultivoClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CultivoAdapter cultivoAdapter;
    private List<Cultivo> cultivoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerViewCultivos = findViewById(R.id.recyclerViewCultivos);
        recyclerViewCultivos.setLayoutManager(new LinearLayoutManager(this));
        cultivoList = new ArrayList<>();
        cultivoAdapter = new CultivoAdapter(cultivoList, this);
        recyclerViewCultivos.setAdapter(cultivoAdapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> abrirDialogoAgregarCultivo());

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        });

        loadCultivos("nombre");
    }

    private void loadCultivos(String orderBy) {
        db.collection("cosechas").orderBy(orderBy, Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(HomeActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    cultivoList.clear();
                    assert snapshots != null;
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        Cultivo cultivo = doc.toObject(Cultivo.class);
                        if (cultivo != null) {
                            cultivo.setId(doc.getId());
                            cultivoList.add(cultivo);
                        }
                    }
                    cultivoAdapter.setCultivos(cultivoList);
                });
    }

    private void abrirDialogoAgregarCultivo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar Cultivo");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nombreInput = new EditText(this);
        nombreInput.setHint("Nombre");
        layout.addView(nombreInput);

        final EditText cantidadInput = new EditText(this);
        cantidadInput.setHint("Cantidad");
        cantidadInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(cantidadInput);

        final EditText fechaInput = new EditText(this);
        fechaInput.setHint("Fecha (dd/MM/yyyy)");
        fechaInput.setInputType(InputType.TYPE_CLASS_DATETIME);
        fechaInput.setFocusable(false);
        layout.addView(fechaInput);

        fechaInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                fechaInput.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        builder.setView(layout);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = nombreInput.getText().toString();
            int cantidad = Integer.parseInt(cantidadInput.getText().toString());

            // Convertir la fecha ingresada a Timestamp
            String[] dateParts = fechaInput.getText().toString().split("/");
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
            selectedDate.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1); // Enero es 0
            selectedDate.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));
            Timestamp fechaTimestamp = new Timestamp(selectedDate.getTime());

            agregarCultivo(nombre, cantidad, fechaTimestamp);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void agregarCultivo(String nombre, int cantidad, Timestamp fecha) {
        Cultivo nuevoCultivo = new Cultivo(nombre, cantidad, fecha);
        db.collection("cosechas").add(nuevoCultivo)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Cultivo agregado", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al agregar cultivo", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.sortByName) {
            loadCultivos("nombre");
            return true;
        } else if (itemId == R.id.sortByDate) {
            loadCultivos("fecha");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEditClick(Cultivo cultivo) {
        abrirDialogoEditarCultivo(cultivo);
    }

    @Override
    public void onDeleteClick(Cultivo cultivo) {
        db.collection("cosechas").document(cultivo.getId())
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(HomeActivity.this, "Cultivo eliminado", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Error al eliminar cultivo", Toast.LENGTH_SHORT).show());
    }

    private void abrirDialogoEditarCultivo(Cultivo cultivo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Cultivo");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nombreInput = new EditText(this);
        nombreInput.setHint("Nombre");
        nombreInput.setText(cultivo.getNombre());
        layout.addView(nombreInput);

        final EditText cantidadInput = new EditText(this);
        cantidadInput.setHint("Cantidad");
        cantidadInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        cantidadInput.setText(String.valueOf(cultivo.getCantidad()));
        layout.addView(cantidadInput);

        builder.setView(layout);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoNombre = nombreInput.getText().toString();
            int nuevaCantidad = Integer.parseInt(cantidadInput.getText().toString());

            actualizarCultivo(cultivo.getId(), nuevoNombre, nuevaCantidad);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void actualizarCultivo(String cultivoId, String nuevoNombre, int nuevaCantidad) {
        db.collection("cosechas").document(cultivoId)
                .update("nombre", nuevoNombre, "cantidad", nuevaCantidad)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(HomeActivity.this, "Cultivo actualizado", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(HomeActivity.this, "Error al actualizar cultivo", Toast.LENGTH_SHORT).show();
                });
    }
}

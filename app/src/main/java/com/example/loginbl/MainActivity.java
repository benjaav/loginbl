package com.example.loginbl;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private TextView mensajeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.pass);
        mensajeTextView = findViewById(R.id.mensaje);

        Button registrarButton = findViewById(R.id.registrar);
        Button ingresarButton = findViewById(R.id.Ingresar);

        // Redirige al RegisterActivity
        registrarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        ingresarButton.setOnClickListener(v -> {
            if (validarDatos()) {
                iniciarSesion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            navegarAHome();
        }
    }

    private void iniciarSesion() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            mensajeTextView.setText("Ingrese su correo y contraseña.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        mensajeTextView.setText("Inicio de sesión exitoso.");
                        navegarAHome();
                    } else {
                        mensajeTextView.setText("Error: " + task.getException().getMessage());
                    }
                });
    }

    private void navegarAHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validarDatos() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mensajeTextView.setText("Ingrese su correo electrónico.");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            mensajeTextView.setText("Ingrese su contraseña.");
            return false;
        }

        return true;
    }
}
